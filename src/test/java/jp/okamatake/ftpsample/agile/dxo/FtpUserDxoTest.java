package jp.okamatake.ftpsample.agile.dxo;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class FtpUserDxoTest {

	private FtpUserDxo ftpUserDxo;

	@Test
	public void testConvert() {
		FtpUser ftpUser = new FtpUser();
		ftpUser.userid = "test";
		ftpUser.userpassword = "password";
		ftpUser.homedirectory = "/home/test/";
		ftpUser.enableflag = true;
		ftpUser.writepermission = true;
		ftpUser.idletime = 10;
		ftpUser.uploadrate = 20;
		ftpUser.downloadrate = 30;
		ftpUser.maxloginnumber = 4;
		ftpUser.maxloginperip = 5;
		User user = ftpUserDxo.convert(ftpUser);
		assertThat("getName", user.getName(), is(ftpUser.userid));
		assertThat("getPassword", user.getPassword(), is(ftpUser.userpassword));
		assertThat("getEnabled", user.getEnabled(), is(true));
		assertThat("getHomeDirectory", user.getHomeDirectory(), is(ftpUser.homedirectory));
		assertThat("getMaxIdleTime", user.getMaxIdleTime(), is(ftpUser.idletime));

		assertThat("WritePermission", user.authorize(new WriteRequest()), is(not(nullValue())));
		TransferRateRequest transferRateRequest = new TransferRateRequest();
		transferRateRequest.setMaxDownloadRate(ftpUser.downloadrate);
		transferRateRequest.setMaxUploadRate(ftpUser.uploadrate);
		assertThat("TransferRatePermission", user.authorize(transferRateRequest), is(not(nullValue())));
		assertThat("ConcurrentLoginPermission", user.authorize(new ConcurrentLoginRequest(ftpUser.maxloginnumber, ftpUser.maxloginperip)), is(not(nullValue())));
	}

	@Test
	public void testConvert_NullならNull() {
		assertThat(ftpUserDxo.convert((FtpUser) null), nullValue());
	}

	@Test
	public void testConvert_WritePermissionなし() {
		FtpUser ftpUser = new FtpUser();
		ftpUser.writepermission = false;
		User user = ftpUserDxo.convert(ftpUser);
		assertThat("WritePermission", user.authorize(new WriteRequest()), is(nullValue()));
	}

	@Test
	public void testConvert_TransferPermissionなし() {
		FtpUser ftpUser = new FtpUser();
		User user = ftpUserDxo.convert(ftpUser);
		assertThat("TransferRatePermission", user.authorize(new TransferRateRequest()), is(nullValue()));
	}

	@Test
	public void testConvert_TransferPermissionの値以下なら認証失敗() {
		FtpUser ftpUser = new FtpUser();
		ftpUser.uploadrate = 20;
		ftpUser.downloadrate = 30;
		User user = ftpUserDxo.convert(ftpUser);
		TransferRateRequest transferRateRequest = new TransferRateRequest();
		transferRateRequest.setMaxDownloadRate(ftpUser.downloadrate + 1);
		transferRateRequest.setMaxUploadRate(ftpUser.uploadrate);
		assertThat("downloadrate", user.authorize(transferRateRequest), is(not(nullValue())));
		transferRateRequest.setMaxDownloadRate(ftpUser.downloadrate);
		transferRateRequest.setMaxUploadRate(ftpUser.uploadrate + 1);
		assertThat("downloadrate", user.authorize(transferRateRequest), is(not(nullValue())));
	}

	@Test
	public void testConvert_ConcurrentLoginPermissionなし() {
		FtpUser ftpUser = new FtpUser();
		User user = ftpUserDxo.convert(ftpUser);
		assertThat("maxloginnumber", user.authorize(new ConcurrentLoginRequest(0, 0)), is(nullValue()));
	}

	@Test
	public void testConvert_ConcurrentLoginPermissionの値以下なら認証失敗() {
		FtpUser ftpUser = new FtpUser();
		ftpUser.maxloginnumber = 4;
		ftpUser.maxloginperip = 5;
		User user = ftpUserDxo.convert(ftpUser);
		assertThat("maxloginnumber", user.authorize(new ConcurrentLoginRequest(5, 5)), is(nullValue()));
		assertThat("maxloginperip", user.authorize(new ConcurrentLoginRequest(4, 6)), is(nullValue()));
	}

	@Test
	public void testConvertToFtpUser() {
		BaseUser user = new BaseUser();
		user.setName("test");
		user.setPassword("password");
		user.setEnabled(true);
		user.setHomeDirectory("/home/test");
		user.setMaxIdleTime(100);

		List<Authority> authorities = new ArrayList<>();
		authorities.add(new WritePermission());
		authorities.add(new TransferRatePermission(200, 300));
		authorities.add(new ConcurrentLoginPermission(400, 500));
		user.setAuthorities(authorities);

		FtpUser ftpUser = ftpUserDxo.convert(user);
		assertThat("getName", ftpUser.userid, is(user.getName()));
		assertThat("getPassword", ftpUser.userpassword, is(user.getPassword()));
		assertThat("getEnabled", ftpUser.enableflag, is(true));
		assertThat("getHomeDirectory", ftpUser.homedirectory, is(user.getHomeDirectory()));
		assertThat("getMaxIdleTime", ftpUser.idletime, is(user.getMaxIdleTime()));
		assertThat("writepermission", ftpUser.writepermission, is(true));
		assertThat("downloadrate", ftpUser.downloadrate, is(200));
		assertThat("uploadrate", ftpUser.uploadrate, is(300));
		assertThat("maxloginnumber", ftpUser.maxloginnumber, is(400));
		assertThat("maxloginperip", ftpUser.maxloginperip, is(500));
	}

	@Test
	public void testConvertToFtpUser_WritePermissionなし() {
		BaseUser user = new BaseUser();
		user.setName("test");
		user.setPassword("password");
		user.setEnabled(true);
		user.setHomeDirectory("/home/test");
		user.setMaxIdleTime(100);

		List<Authority> authorities = new ArrayList<>();
		authorities.add(new TransferRatePermission(200, 300));
		authorities.add(new ConcurrentLoginPermission(400, 500));
		user.setAuthorities(authorities);

		FtpUser ftpUser = ftpUserDxo.convert(user);
		assertThat("getName", ftpUser.userid, is(user.getName()));
		assertThat("getPassword", ftpUser.userpassword, is(user.getPassword()));
		assertThat("getEnabled", ftpUser.enableflag, is(true));
		assertThat("getHomeDirectory", ftpUser.homedirectory, is(user.getHomeDirectory()));
		assertThat("getMaxIdleTime", ftpUser.idletime, is(user.getMaxIdleTime()));
		assertThat("writepermission", ftpUser.writepermission, is(false));
		assertThat("downloadrate", ftpUser.downloadrate, is(200));
		assertThat("uploadrate", ftpUser.uploadrate, is(300));
		assertThat("maxloginnumber", ftpUser.maxloginnumber, is(400));
		assertThat("maxloginperip", ftpUser.maxloginperip, is(500));
	}

	@Test
	public void testConvertToFtpUser_TransferRatePermissionなし() {
		BaseUser user = new BaseUser();
		user.setName("test");
		user.setPassword("password");
		user.setEnabled(true);
		user.setHomeDirectory("/home/test");
		user.setMaxIdleTime(100);

		List<Authority> authorities = new ArrayList<>();
		authorities.add(new WritePermission());
		authorities.add(new ConcurrentLoginPermission(400, 500));
		user.setAuthorities(authorities);

		FtpUser ftpUser = ftpUserDxo.convert(user);
		assertThat("getName", ftpUser.userid, is(user.getName()));
		assertThat("getPassword", ftpUser.userpassword, is(user.getPassword()));
		assertThat("getEnabled", ftpUser.enableflag, is(true));
		assertThat("getHomeDirectory", ftpUser.homedirectory, is(user.getHomeDirectory()));
		assertThat("getMaxIdleTime", ftpUser.idletime, is(user.getMaxIdleTime()));
		assertThat("writepermission", ftpUser.writepermission, is(true));
		assertThat("downloadrate", ftpUser.downloadrate, is(0));
		assertThat("uploadrate", ftpUser.uploadrate, is(0));
		assertThat("maxloginnumber", ftpUser.maxloginnumber, is(400));
		assertThat("maxloginperip", ftpUser.maxloginperip, is(500));
	}


	@Test
	public void testConvertToFtpUser_ConcurrentLoginPermissionなし() {
		BaseUser user = new BaseUser();
		user.setName("test");
		user.setPassword("password");
		user.setEnabled(true);
		user.setHomeDirectory("/home/test");
		user.setMaxIdleTime(100);

		List<Authority> authorities = new ArrayList<>();
		authorities.add(new WritePermission());
		authorities.add(new TransferRatePermission(200, 300));
		user.setAuthorities(authorities);

		FtpUser ftpUser = ftpUserDxo.convert(user);
		assertThat("getName", ftpUser.userid, is(user.getName()));
		assertThat("getPassword", ftpUser.userpassword, is(user.getPassword()));
		assertThat("getEnabled", ftpUser.enableflag, is(true));
		assertThat("getHomeDirectory", ftpUser.homedirectory, is(user.getHomeDirectory()));
		assertThat("getMaxIdleTime", ftpUser.idletime, is(user.getMaxIdleTime()));
		assertThat("writepermission", ftpUser.writepermission, is(true));
		assertThat("downloadrate", ftpUser.downloadrate, is(200));
		assertThat("uploadrate", ftpUser.uploadrate, is(300));
		assertThat("maxloginnumber", ftpUser.maxloginnumber, is(0));
		assertThat("maxloginperip", ftpUser.maxloginperip, is(0));
	}
}
