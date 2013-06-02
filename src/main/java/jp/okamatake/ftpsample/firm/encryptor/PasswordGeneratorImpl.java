package jp.okamatake.ftpsample.firm.encryptor;

import java.security.MessageDigest;

import jp.okamatake.ftpsample.agile.entity.FtpUser;

import org.apache.ftpserver.util.StringUtils;
import org.seasar.framework.util.MessageDigestUtil;

/**
 * @author okamoto
 */
public class PasswordGeneratorImpl implements PasswordGenerator {
	
	private String fixedSalt;
	
	private String algorythm;
	
	public PasswordGeneratorImpl(String fixedSalt, String algorythm) {
		this.fixedSalt = fixedSalt;
		this.algorythm = algorythm;
	}

	@Override
	public String getPasswordHash(FtpUser user) {
		final String salt = getSalt(user);
		MessageDigest SHA1 = MessageDigestUtil.getInstance(algorythm);
		
		String password = user.userpassword + salt;
		byte[] hash = SHA1.digest(password.getBytes());
		return StringUtils.toHexString(hash);
	}
	
	private String getSalt(FtpUser user) {
		return user.userid + fixedSalt;
	}
}
