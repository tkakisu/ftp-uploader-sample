package jp.okamatake.ftpsample.agile.service;

import static jp.okamatake.ftpsample.agile.entity.FtpUserNames.userid;
import static org.seasar.extension.jdbc.operation.Operations.asc;

import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Resource;

import jp.okamatake.ftpsample.agile.entity.FtpUser;
import jp.okamatake.ftpsample.firm.encryptor.PasswordGenerator;

import org.seasar.framework.util.StringUtil;

/**
 * {@link FtpUser}のサービスクラスです。
 *
 */
@Generated(value = {"S2JDBC-Gen 2.4.45", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl"}, date = "2012/03/17 23:30:32")
public class FtpUserService extends AbstractService<FtpUser> {

	public static final String ADMIN_NAME = "admin";

	public static final String ANONYMOUS_NAME = "anonymous";

	@Resource
	protected PasswordGenerator passwordGenerator;

    /**
     * 識別子でエンティティを検索します。
     *
     * @param userid
     *            識別子
     * @return エンティティ
     */
    public FtpUser findById(String userid) {
        return select().id(userid).getSingleResult();
    }

    /**
     * 識別子の昇順ですべてのエンティティを検索します。
     *
     * @return エンティティのリスト
     */
    public List<FtpUser> findAllOrderById() {
        return select().orderBy(asc(userid())).getResultList();
    }

	public List<String> getAllUserName() {
		return jdbcManager.selectBySqlFile(String.class, "sql/ftpUser/getAllUserName.sql").getResultList();
	}

	public boolean isAdmin(FtpUser user) {
		return StringUtil.equals(user.userid, ADMIN_NAME);
	}

	public FtpUser authenticate(String userid, String password) {
		FtpUser origin = findById(userid);
		if (origin == null) {
			return null;
		}

		FtpUser key = new FtpUser();
		key.userid = userid;
		key.userpassword = password;
		String hash = passwordGenerator.getPasswordHash(key);

		return StringUtil.equals(origin.userpassword, hash) ? origin : null;
	}
}