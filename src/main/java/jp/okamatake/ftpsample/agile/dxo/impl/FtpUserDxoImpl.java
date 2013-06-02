package jp.okamatake.ftpsample.agile.dxo.impl;

import java.util.ArrayList;
import java.util.List;

import jp.okamatake.ftpsample.agile.dxo.FtpUserDxo;
import jp.okamatake.ftpsample.agile.entity.FtpUser;

import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginRequest;
import org.apache.ftpserver.usermanager.impl.TransferRatePermission;
import org.apache.ftpserver.usermanager.impl.TransferRateRequest;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.apache.ftpserver.usermanager.impl.WriteRequest;

public class FtpUserDxoImpl implements FtpUserDxo {

	@Override
	public User convert(FtpUser ftpUser) {
		if (ftpUser == null) {
			return null;
		}

		BaseUser user = new BaseUser();
		user.setName(ftpUser.userid);
		user.setPassword(ftpUser.userpassword);
		user.setHomeDirectory(ftpUser.homedirectory);

		if (ftpUser.enableflag != null) {
			user.setEnabled(ftpUser.enableflag);
		}

		if (ftpUser.idletime != null) {
			user.setMaxIdleTime(ftpUser.idletime);
		}

		user.setAuthorities(getAuthorities(ftpUser));

		return user;
	}

	private List<Authority> getAuthorities(FtpUser ftpUser) {
		List<Authority> authorities = new ArrayList<>();
		if (ftpUser.writepermission != null && ftpUser.writepermission) {
			authorities.add(new WritePermission());
		}

		if (ftpUser.downloadrate != null && ftpUser.uploadrate != null) {
			TransferRatePermission transferRatePermission =
					new TransferRatePermission(ftpUser.downloadrate, ftpUser.uploadrate);
			authorities.add(transferRatePermission);
		}

		if (ftpUser.maxloginnumber != null && ftpUser.maxloginperip != null) {
			ConcurrentLoginPermission concurrentLoginPermission =
					new ConcurrentLoginPermission(ftpUser.maxloginnumber, ftpUser.maxloginperip);
			authorities.add(concurrentLoginPermission);
		}

		return authorities;
	}

	@Override
	public FtpUser convert(User user) {
		FtpUser ftpUser = new FtpUser();

		ftpUser.userid = user.getName();
		ftpUser.userpassword = user.getPassword();
		ftpUser.homedirectory = user.getHomeDirectory();
		ftpUser.enableflag = user.getEnabled();
		ftpUser.idletime = user.getMaxIdleTime();

		boolean hasWritePermission = user.authorize(new WriteRequest()) != null;
		ftpUser.writepermission = hasWritePermission;

		TransferRateRequest transferRateRequest = (TransferRateRequest) user.authorize(new TransferRateRequest());
		if (transferRateRequest != null) {
			ftpUser.uploadrate = transferRateRequest.getMaxUploadRate();
			ftpUser.downloadrate = transferRateRequest.getMaxDownloadRate();
		} else {
			ftpUser.uploadrate = 0;
			ftpUser.downloadrate = 0;
		}

		ConcurrentLoginRequest concurrentLoginRequest = (ConcurrentLoginRequest) user.authorize(new ConcurrentLoginRequest(0, 0));
		if (concurrentLoginRequest != null) {
			ftpUser.maxloginnumber = concurrentLoginRequest.getMaxConcurrentLogins();
			ftpUser.maxloginperip = concurrentLoginRequest.getMaxConcurrentLoginsPerIP();
		} else {
			ftpUser.maxloginnumber = 0;
			ftpUser.maxloginperip = 0;
		}

		return ftpUser;
	}
}
