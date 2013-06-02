package jp.okamatake.ftpsample.agile.dxo;

import jp.okamatake.ftpsample.agile.entity.FtpUser;

import org.apache.ftpserver.ftplet.User;

public interface FtpUserDxo {
	User convert(FtpUser ftpUser);

	FtpUser convert(User user);
}
