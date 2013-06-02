package jp.okamatake.ftpsample.firm.usermanager;

import jp.okamatake.ftpsample.agile.dxo.FtpUserDxo;
import jp.okamatake.ftpsample.agile.service.FtpUserService;

import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.UserManagerFactory;
import org.seasar.framework.container.SingletonS2Container;

public class JDBCUserManagerFactory implements UserManagerFactory {

	@Override
	public UserManager createUserManager() {
		FtpUserService ftpUserService = SingletonS2Container.getComponent(FtpUserService.class);
		FtpUserDxo ftpUserDxo = SingletonS2Container.getComponent(FtpUserDxo.class);
		JDBCUserManager userManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		return userManager;
	}

}
