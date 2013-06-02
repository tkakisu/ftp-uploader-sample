package jp.okamatake.ftpsample.firm.encryptor;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import jp.okamatake.ftpsample.agile.entity.FtpUser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class PasswordGeneratorImplTest {

	PasswordGeneratorImpl passwordGeneratorImpl;
	
	@Test
	public void test() {
		FtpUser user = new FtpUser();
		user.userid = "test";
		user.userpassword = "password";
		assertThat(passwordGeneratorImpl.getPasswordHash(user), is("D406A95F208EF02E036E9FA63210B6A29274C3C12021D82568E686E309D6CB5C"));
	}

}
