package jp.okamatake.ftpsample.firm.encryptor;

import jp.okamatake.ftpsample.agile.entity.FtpUser;

public interface PasswordGenerator {

	String getPasswordHash(FtpUser user);
}
