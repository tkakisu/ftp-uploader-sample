package jp.okamatake.ftpsample.agile.entity;

import javax.annotation.Generated;
import jp.okamatake.ftpsample.agile.entity.FtpImageNames._FtpImageNames;
import jp.okamatake.ftpsample.agile.entity.FtpUserNames._FtpUserNames;

/**
 * 名前クラスの集約です。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.45", "org.seasar.extension.jdbc.gen.internal.model.NamesAggregateModelFactoryImpl"}, date = "2012/04/12 10:28:05")
public class Names {

    /**
     * {@link FtpImage}の名前クラスを返します。
     * 
     * @return FtpImageの名前クラス
     */
    public static _FtpImageNames ftpImage() {
        return new _FtpImageNames();
    }

    /**
     * {@link FtpUser}の名前クラスを返します。
     * 
     * @return FtpUserの名前クラス
     */
    public static _FtpUserNames ftpUser() {
        return new _FtpUserNames();
    }
}