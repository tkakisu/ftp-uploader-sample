package jp.okamatake.ftpsample.firm;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.seasar.framework.container.servlet.S2ContainerServlet;

/**
 * 多分、ここでこんなことするより、まともなDbUserManagerとパスワード管理処理を作った方が良い
 * 
 * @author okamoto
 */
public class FtpS2ContainerServlet extends S2ContainerServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		super.init();
		
		try {
			FtpServerManager manager = new FtpServerManager();
			FtpServer server = manager.createFtpServer();
			server.start();
		}
		catch (FtpException e) {
			throw new RuntimeException(e);
		}
	}
}
