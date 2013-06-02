package jp.okamatake.ftpsample.firm.command;

import java.io.IOException;

import jp.okamatake.ftpsample.agile.entity.FtpImage;
import jp.okamatake.ftpsample.agile.service.FtpImageService;

import org.apache.ftpserver.command.Command;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedFtpReply;
import org.seasar.framework.container.SingletonS2Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link org.apache.ftpserver.command.impl.DELE DELE}コマンドの代替。
 * 
 * @author okamoto
 */
public class JDBCDELE implements Command {


    private final Logger LOG = LoggerFactory.getLogger(JDBCDELE.class);

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session,
            final FtpServerContext context, final FtpRequest request)
            throws IOException, FtpException {

        // reset state variables
        session.resetState();

        // argument check
        String fileName = request.getArgument().substring(1); // "パスの部分を削除。
        if (fileName == null) {
            session.write(LocalizedFtpReply.translate(session, request, context,
                    FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS,
                    "DELE", null));
            return;
        }

        FtpImageService ftpImageService = SingletonS2Container.getComponent(FtpImageService.class);
        String userName = session.getUser().getName();
		FtpImage image = ftpImageService.findByUserIdFileName(userName, fileName);
		if (image == null) {
            session.write(LocalizedFtpReply.translate(session, request, context,
                    FtpReply.REPLY_450_REQUESTED_FILE_ACTION_NOT_TAKEN, "DELE",
                    fileName));
            return;
		}

		try {
			ftpImageService.delete(image);

			session.write(LocalizedFtpReply.translate(session, request, context,
                    FtpReply.REPLY_250_REQUESTED_FILE_ACTION_OKAY, "DELE",
                    fileName));

            // log message
            LOG.info("File delete : " + userName + " - " + fileName);
		}
		catch (Exception e) {
            session.write(LocalizedFtpReply.translate(session, request, context,
                    FtpReply.REPLY_450_REQUESTED_FILE_ACTION_NOT_TAKEN, "DELE",
                    fileName));
            LOG.error(String.format("File delete error: userName=%s, fileName=%s, message=%s", userName, fileName, e.getMessage()));
        }
    }

}
