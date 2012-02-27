package jp.okamatake.ftpsample.firm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import jp.okamatake.ftpsample.agile.entity.FtpImage;
import jp.okamatake.ftpsample.agile.entity.FtpImageNames;
import jp.okamatake.ftpsample.agile.service.FtpImageService;

import org.apache.ftpserver.command.Command;
import org.apache.ftpserver.ftplet.DataConnection;
import org.apache.ftpserver.ftplet.DataConnectionFactory;
import org.apache.ftpserver.ftplet.DefaultFtpReply;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.IODataConnectionFactory;
import org.apache.ftpserver.impl.LocalizedFtpReply;
import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.container.SingletonS2Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * STORコマンドの代替。
 * 多分、IoSessionに変わるSessionを作るべき。
 * 
 * @author okamoto
 */
public class JDBCSTOR implements Command {

    private final Logger LOG = LoggerFactory.getLogger(JDBCSTOR.class);

    /**
     * Execute command.
     */
    @Override
    public void execute(final FtpIoSession session,
            final FtpServerContext context, final FtpRequest request)
            throws IOException, FtpException {

        try {

            // get state variable
            @SuppressWarnings("unused")
			long skipLen = session.getFileOffset();

            // argument check
            String fileName = request.getArgument();
            if (fileName == null) {
                session
                        .write(LocalizedFtpReply
                                .translate(
                                        session,
                                        request,
                                        context,
                                        FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS,
                                        "STOR", null));
                return;
            }

            // 24-10-2007 - added check if PORT or PASV is issued, see
            // https://issues.apache.org/jira/browse/FTPSERVER-110
            DataConnectionFactory connFactory = session.getDataConnection();
            if (connFactory instanceof IODataConnectionFactory) {
                InetAddress address = ((IODataConnectionFactory) connFactory)
                        .getInetAddress();
                if (address == null) {
                    session.write(new DefaultFtpReply(
                            FtpReply.REPLY_503_BAD_SEQUENCE_OF_COMMANDS,
                            "PORT or PASV must be issued first"));
                    return;
                }
            }

            // get filename
            FtpFile file = null;
            try {
                file = session.getFileSystemView().getFile(fileName);
            } catch (Exception ex) {
                LOG.debug("Exception getting file object", ex);
            }
            if (file == null) {
                session.write(LocalizedFtpReply.translate(session, request, context,
                        FtpReply.REPLY_550_REQUESTED_ACTION_NOT_TAKEN,
                        "STOR.invalid", fileName));
                return;
            }

            // get data connection
            session.write(
                    LocalizedFtpReply.translate(session, request, context,
                            FtpReply.REPLY_150_FILE_STATUS_OKAY, "STOR",
                            fileName)).awaitUninterruptibly(10000);

            DataConnection dataConnection;
            try {
                dataConnection = session.getDataConnection().openConnection();
            } catch (Exception e) {
                LOG.debug("Exception getting the input data stream", e);
                session.write(LocalizedFtpReply.translate(session, request, context,
                        FtpReply.REPLY_425_CANT_OPEN_DATA_CONNECTION, "STOR",
                        fileName));
                return;
            }

            boolean success = setFtpImage(session, context, request, dataConnection, fileName);

			// if data transfer ok - send transfer complete message
			if (success) {
				session.write(LocalizedFtpReply.translate(session, request,
						context, FtpReply.REPLY_226_CLOSING_DATA_CONNECTION,
						"STOR", fileName));
			}
        } finally {
            session.resetState();
            session.getDataConnection().closeDataConnection();
        }
    }
    
    protected boolean setFtpImage(final FtpIoSession session,final FtpServerContext context, final FtpRequest request, final DataConnection dataConnection, final String fileName) {
		boolean result = false;
		
		final FtpImageService ftpImageService = SingletonS2Container.getComponent(FtpImageService.class);
		final FtpImage ftpImage = getFtpImage(session.getUser(), fileName, ftpImageService);

		// transfer data
		try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
			@SuppressWarnings("unused")
			long transSz = dataConnection.transferFromClient(
					session.getFtpletSession(), outStream);

			// attempt to close the output stream so that errors in
			// closing it will return an error to the client (FTPSERVER-119)
			// XXX: transSzが0だった場合の処理
			if (outStream != null) {
				outStream.close();
			}

			LOG.info("File uploaded {}", fileName);
			
			ftpImage.image = outStream.toByteArray();
			ftpImage.updatedDate = new Timestamp(new Date().getTime());

			if (ftpImageService.update(ftpImage) == 0) {
				ftpImageService.insert(ftpImage);
			}
			
			result = true;
		} catch (SocketException ex) {
			LOG.debug("Socket exception during data transfer", ex);
			session.write(LocalizedFtpReply.translate(session, request,
					context,
					FtpReply.REPLY_426_CONNECTION_CLOSED_TRANSFER_ABORTED,
					"STOR", fileName));
		} catch (IOException ex) {
			LOG.debug("IOException during data transfer", ex);
			session.write(LocalizedFtpReply
					.translate(
							session,
							request,
							context,
							FtpReply.REPLY_551_REQUESTED_ACTION_ABORTED_PAGE_TYPE_UNKNOWN,
							"STOR", fileName));
		}
		
		return result;
    }

    /**
     * データベースから{@link FtpImage}を取得します。
     * 
     * @param user
     * @param fileName
     * @param ftpImageService
     * @return DBに同名のファイルが存在する場合はそれを返す。新規ファイルの場合は、{@code user}, {@code fileName}が設定された新規オブジェクトを返す。
     */
	protected FtpImage getFtpImage(final User user, final String fileName,
			final FtpImageService ftpImageService) {
		final BeanMap conditions = new BeanMap();
		conditions.put(FtpImageNames.userid().toString(), user.getName());
		conditions.put(FtpImageNames.filename().toString(), fileName);
		List<FtpImage> list = ftpImageService.findByCondition(conditions);
		FtpImage ftpImage;
		if (list.isEmpty()) {
			ftpImage = new FtpImage();
			ftpImage.userid = user.getName();
			ftpImage.filename = fileName;
		}
		else {
			ftpImage = list.get(0);
		}
		return ftpImage;
	}
}
