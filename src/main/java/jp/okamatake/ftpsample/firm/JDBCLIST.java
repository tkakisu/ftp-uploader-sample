package jp.okamatake.ftpsample.firm;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.ftpserver.command.Command;
import org.apache.ftpserver.command.impl.listing.ListArgument;
import org.apache.ftpserver.command.impl.listing.ListArgumentParser;
import org.apache.ftpserver.ftplet.DataConnection;
import org.apache.ftpserver.ftplet.DataConnectionFactory;
import org.apache.ftpserver.ftplet.DefaultFtpReply;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.IODataConnectionFactory;
import org.apache.ftpserver.impl.LocalizedFtpReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link org.apache.ftpserver.command.impl.LIST LIST}コマンドの代替。
 * 
 * @author okamoto
 */
public class JDBCLIST implements Command {

    private final Logger LOG = LoggerFactory.getLogger(JDBCLIST.class);

    private JDBCDirectoryLister directoryLister = new JDBCDirectoryLister();

    /**
     * Execute command.
     */
    public void execute(final FtpIoSession session,
            final FtpServerContext context, final FtpRequest request)
            throws IOException, FtpException {

        try {
            // reset state variables
            session.resetState();

            // parse argument
            ListArgument parsedArg = ListArgumentParser.parse(request
                    .getArgument());

//            // checl that the directory or file exists
//            FtpFile file = session.getFileSystemView().getFile(parsedArg.getFile());
//            
//            if(!file.doesExist()) {
//                LOG.debug("Listing on a non-existing file");
//                session.write(LocalizedFtpReply.translate(session, request, context,
//                        FtpReply.REPLY_450_REQUESTED_FILE_ACTION_NOT_TAKEN, "LIST",
//                        null));             
//                return;
//            }
            
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

            // get data connection
            session.write(LocalizedFtpReply.translate(session, request, context,
                    FtpReply.REPLY_150_FILE_STATUS_OKAY, "LIST", null));

            DataConnection dataConnection;
            try {
                dataConnection = session.getDataConnection().openConnection();
            } catch (Exception e) {
                LOG.debug("Exception getting the output data stream", e);
                session.write(LocalizedFtpReply.translate(session, request, context,
                        FtpReply.REPLY_425_CANT_OPEN_DATA_CONNECTION, "LIST",
                        null));
                return;
            }

            // transfer listing data
            boolean failure = false;

            try {
            	// FIXME: directoryListerをserviceに置換
                dataConnection.transferToClient(session.getFtpletSession(), directoryLister.listFiles(parsedArg, session));
            } catch (SocketException ex) {
                LOG.debug("Socket exception during list transfer", ex);
                failure = true;
                session.write(LocalizedFtpReply.translate(session, request, context,
                        FtpReply.REPLY_426_CONNECTION_CLOSED_TRANSFER_ABORTED,
                        "LIST", null));
            } catch (IOException ex) {
                LOG.debug("IOException during list transfer", ex);
                failure = true;
                session
                        .write(LocalizedFtpReply
                                .translate(
                                        session,
                                        request,
                                        context,
                                        FtpReply.REPLY_551_REQUESTED_ACTION_ABORTED_PAGE_TYPE_UNKNOWN,
                                        "LIST", null));
            } catch (IllegalArgumentException e) {
                LOG.debug("Illegal list syntax: " + request.getArgument(), e);
                // if listing syntax error - send message
                session
                        .write(LocalizedFtpReply
                                .translate(
                                        session,
                                        request,
                                        context,
                                        FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS,
                                        "LIST", null));
            }

            // if data transfer ok - send transfer complete message
            if (!failure) {
                session.write(LocalizedFtpReply.translate(session, request, context,
                        FtpReply.REPLY_226_CLOSING_DATA_CONNECTION, "LIST",
                        null));
            }
        } finally {
            session.getDataConnection().closeDataConnection();
        }
    }
}
