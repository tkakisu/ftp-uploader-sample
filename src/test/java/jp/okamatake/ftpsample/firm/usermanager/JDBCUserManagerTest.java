package jp.okamatake.ftpsample.firm.usermanager;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.okamatake.ftpsample.agile.dxo.FtpUserDxo;
import jp.okamatake.ftpsample.agile.entity.FtpUser;
import jp.okamatake.ftpsample.agile.service.FtpUserService;

import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.ftplet.AuthenticationFailedException;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.usermanager.AnonymousAuthentication;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.TransferRatePermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class JDBCUserManagerTest {

	private FtpUserService ftpUserService;

	private FtpUserDxo ftpUserDxo;

	@Test
	public void testGetUserByName() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		User user = jdbcUserManager.getUserByName("test");
		assertThat(user.getPassword(), is("D406A95F208EF02E036E9FA63210B6A29274C3C12021D82568E686E309D6CB5C"));
	}

	@Test
	public void testGetAllUserNames() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		String[] userNames = jdbcUserManager.getAllUserNames();
		assertThat(Arrays.asList(userNames), hasItem("test"));
	}

	@Test
	public void testDelete() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		jdbcUserManager.delete("test");
		assertThat(jdbcUserManager.getUserByName("test"), is(nullValue()));
	}

	@Test
	public void testDelete_存在しないユーザー名でもエラーにならないこと() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		jdbcUserManager.delete("hogehoge");
	}

	@Test
	public void testSave_アップデートの場合() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
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

		jdbcUserManager.save(user);

		FtpUser ftpUser = ftpUserService.findById("test");
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
		assertThat("version", ftpUser.version, is(Long.valueOf(2L)));
	}

	@Test
	public void testSave_インサートの場合() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		BaseUser user = new BaseUser();
		user.setName("hoge");
		user.setPassword("password");
		user.setEnabled(true);
		user.setHomeDirectory("/home/hoge");
		user.setMaxIdleTime(100);

		List<Authority> authorities = new ArrayList<>();
		authorities.add(new WritePermission());
		authorities.add(new TransferRatePermission(200, 300));
		authorities.add(new ConcurrentLoginPermission(400, 500));
		user.setAuthorities(authorities);

		jdbcUserManager.save(user);

		FtpUser ftpUser = ftpUserService.findById("hoge");
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
		assertThat("version", ftpUser.version, is(Long.valueOf(1L)));
	}

	@Test
	public void testDoesExist_あり() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		assertThat(jdbcUserManager.doesExist("test"), is(true));
	}

	@Test
	public void testDoesExist_なし() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		assertThat(jdbcUserManager.doesExist("hoge"), is(false));
	}

	@Test
	public void testAuthenticate_ユーザーパスワード認証成功() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication("test", "password");
		User user = jdbcUserManager.authenticate(authentication);
		assertThat("getName", user.getName(), is("test"));
		assertThat("getPassword", user.getPassword(), is("D406A95F208EF02E036E9FA63210B6A29274C3C12021D82568E686E309D6CB5C"));
	}

	@Test(expected = AuthenticationFailedException.class)
	public void testAuthenticate_ユーザーパスワード認証失敗() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication("test", "D406A95F208EF02E036E9FA63210B6A29274C3C12021D82568E686E309D6CB5C");
		jdbcUserManager.authenticate(authentication);
		fail();
	}

	@Test
	public void testAuthenticate_アノニマス認証成功() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);

		FtpUser ftpUser = new FtpUser();
		ftpUser.userid = "anonymous";
		ftpUser.userpassword = "anonymous";
		ftpUser.homedirectory = "/temp/";
		ftpUser.enableflag = true;
		ftpUser.writepermission = true;
		ftpUser.idletime = 1000;
		ftpUser.uploadrate = 0;
		ftpUser.downloadrate = 1000;
		ftpUser.maxloginnumber = 10;
		ftpUser.maxloginperip = 10;
		ftpUserService.insert(ftpUser);

		User user = jdbcUserManager.authenticate(new AnonymousAuthentication());
		assertThat("getName", user.getName(), is("anonymous"));
	}

	@Test(expected = AuthenticationFailedException.class)
	public void testAuthenticate_アノニマス認証失敗() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		jdbcUserManager.authenticate(new AnonymousAuthentication());
		fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAuthenticate_未対応のauthentication() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		jdbcUserManager.authenticate(new Authentication() {});
		fail();
	}

	@Test
	public void testGetAdminName() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		assertThat(jdbcUserManager.getAdminName(), is("admin"));
	}

	@Test
	public void testIsAdmin_true() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		assertThat(jdbcUserManager.isAdmin("admin"), is(true));
	}

	@Test
	public void testIsAdmin_false() throws Exception {
		JDBCUserManager jdbcUserManager = new JDBCUserManager(ftpUserService, ftpUserDxo);
		assertThat(jdbcUserManager.isAdmin("test"), is(false));
	}
}
