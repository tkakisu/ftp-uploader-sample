package jp.okamatake.ftpsample.agile.service;

import static jp.okamatake.ftpsample.agile.entity.FtpImageNames.*;
import static org.seasar.extension.jdbc.operation.Operations.*;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Generated;

import jp.okamatake.ftpsample.agile.entity.FtpImage;
import jp.okamatake.ftpsample.agile.entity.FtpImageNames;

import org.seasar.extension.jdbc.where.SimpleWhere;

/**
 * {@link FtpImage}のサービスクラスです。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.45", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl"}, date = "2012/03/17 23:30:32")
public class FtpImageService extends AbstractService<FtpImage> {

    /**
     * 識別子でエンティティを検索します。
     * 
     * @param imageId
     *            識別子
     * @return エンティティ
     */
    public FtpImage findById(BigInteger imageId) {
        return select().id(imageId).getSingleResult();
    }

    /**
     * 識別子の昇順ですべてのエンティティを検索します。
     * 
     * @return エンティティのリスト
     */
    public List<FtpImage> findAllOrderById() {
        return select().orderBy(asc(imageId())).getResultList();
    }
    
    /**
     * ユーザーに該当するエンティティを検索します。
     * 
     * @param userId
     * @return エンティティのリスト
     */
    public List<FtpImage> findByUserId(String userId) {
    	return select().where(eq(FtpImageNames.userid(), userId)).getResultList();
    }
    
    public FtpImage findByUserIdFileName(String userId, String fileName) {
    	return select().where(new SimpleWhere().eq(FtpImageNames.userid(), userId).eq(FtpImageNames.filename(), fileName)).getSingleResult();
    }
}