package jp.okamatake.ftpsample.firm;

import java.io.IOException;

import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Webサービスへのデータ転送用{@link org.apache.ftpserver.ftplet.Ftplet Ftplet}.
 * 
 * 独自コマンドの使用は{@link org.apache.ftpserver.FtpServerFactory#setCommandFactory(org.apache.ftpserver.command.CommandFactory)}で出来るので、このクラスは不要か？
 * @author okamoto
 */
public class DataTransfarFtplet extends DefaultFtplet {
	protected static final Logger log = LoggerFactory.getLogger(DataTransfarFtplet.class);

	@Override
	public FtpletResult afterCommand(FtpSession session, FtpRequest request,
			FtpReply reply) throws FtpException, IOException {
		// TODO: 2段階の転送手順を踏む必要があるか？性能面と障害発生率から検討。必要ならSTORを置き換える。
		// TODO: STOUは必要？
		// TODO: 他のコマンドは殺す？
		if (request.getCommand().equals("STOR")
				&& reply.getCode() == FtpReply.REPLY_226_CLOSING_DATA_CONNECTION) {
			if (log.isDebugEnabled()) {
				log.debug("STOR is Transfer complete. fileName=" + request.getArgument());
			}
			
			// TODO: ファイル転送処理
		}
		return FtpletResult.DEFAULT;
	}
}
