package jp.okamatake.ftpsample.agile.service;

import java.util.List;
import javax.annotation.Generated;

import jp.okamatake.ftpsample.agile.entity.FtpUser;

import static jp.okamatake.ftpsample.agile.entity.FtpUserNames.*;
import static org.seasar.extension.jdbc.operation.Operations.*;

/**
 * {@link FtpUser}のサービスクラスです。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.45", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl"}, date = "2012/03/17 23:30:32")
public class FtpUserService extends AbstractService<FtpUser> {

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
}