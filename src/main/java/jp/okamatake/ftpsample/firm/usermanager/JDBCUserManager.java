package jp.okamatake.ftpsample.firm.usermanager;

import java.util.List;

import jp.okamatake.ftpsample.agile.dxo.FtpUserDxo;
import jp.okamatake.ftpsample.agile.entity.FtpUser;
import jp.okamatake.ftpsample.agile.service.FtpUserService;

import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.ftplet.AuthenticationFailedException;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.AnonymousAuthentication;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.seasar.framework.exception.SQLRuntimeException;

public class JDBCUserManager implements UserManager {

	private final FtpUserService ftpUserService;

	private final FtpUserDxo ftpUserDxo;

	public JDBCUserManager(FtpUserService ftpUserService, FtpUserDxo ftpUserDxo) {
		this.ftpUserService = ftpUserService;
		this.ftpUserDxo = ftpUserDxo;
	}

	@Override
	public User getUserByName(String username) throws FtpException {
		try {
			FtpUser ftpUser = ftpUserService.findById(username);
			return ftpUserDxo.convert(ftpUser);
		} catch (SQLRuntimeException e) {
			throw new FtpException(e);
		}
	}

	@Override
	public String[] getAllUserNames() throws FtpException {
		try {
			List<String> names = ftpUserService.getAllUserName();
			return names.toArray(new String[names.size()]);
		} catch (SQLRuntimeException e) {
			throw new FtpException(e);
		}
	}

	@Override
	public void delete(String username) throws FtpException {
		FtpUser ftpUser = ftpUserService.findById(username);
		if (ftpUser != null) {
			ftpUserService.delete(ftpUser);
		}
	}

	@Override
	public void save(User user) throws FtpException {
		try {
			FtpUser target = ftpUserDxo.convert(user);
			FtpUser origin = ftpUserService.findById(user.getName());

			if (origin == null) {
				ftpUserService.insert(target);
			} else {
				target.version = origin.version;
				ftpUserService.update(target);
			}
		} catch (SQLRuntimeException e) {
			throw new FtpException(e);
		}
	}

	@Override
	public boolean doesExist(String username) throws FtpException {
		return ftpUserService.findById(username) != null;
	}

	@Override
	public User authenticate(Authentication authentication)
			throws AuthenticationFailedException {
		if (authentication instanceof UsernamePasswordAuthentication) {
			return auth((UsernamePasswordAuthentication) authentication);
		} else if (authentication instanceof AnonymousAuthentication) {
			return auth((AnonymousAuthentication) authentication);
		}

		throw new IllegalArgumentException("Authentication not supported by this user manager");
	}

	private User auth(UsernamePasswordAuthentication authentication) throws AuthenticationFailedException {
		FtpUser ftpUser = ftpUserService.authenticate(authentication.getUsername(), authentication.getPassword());
		if (ftpUser == null) {
			throw new AuthenticationFailedException("Authentication failed");
		}

		return ftpUserDxo.convert(ftpUser);
	}

	private User auth(AnonymousAuthentication authentication) throws AuthenticationFailedException {
		try {
			User user = getUserByName(FtpUserService.ANONYMOUS_NAME);
			if (user == null) {
				throw new AuthenticationFailedException("Authentication failed");
			}

			return user;
		} catch (FtpException e) {
			throw new AuthenticationFailedException(e);
		}
	}

	@Override
	public String getAdminName() throws FtpException {
		return FtpUserService.ADMIN_NAME;
	}

	@Override
	public boolean isAdmin(String username) throws FtpException {
		FtpUser ftpUser = new FtpUser();
		ftpUser.userid = username;
		return ftpUserService.isAdmin(ftpUser);
	}

}
