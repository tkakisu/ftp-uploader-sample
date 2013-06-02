package jp.okamatake.ftpsample.agile.entity;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * FtpUserエンティティクラス
 *
 */
@Entity
@Generated(value = {"S2JDBC-Gen 2.4.45", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl"}, date = "2012/04/08 13:05:25")
public class FtpUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /** useridプロパティ */
    @Id
    @Column(length = 64, nullable = false, unique = true)
    public String userid;

    /** userpasswordプロパティ */
    @Column(length = 64, nullable = false, unique = false)
    public String userpassword;

    /** homedirectoryプロパティ */
    @Column(length = 45, nullable = true, unique = false)
    public String homedirectory;

    /** enableflagプロパティ */
    @Column(nullable = true, unique = false)
    public Boolean enableflag;

    /** writepermissionプロパティ */
    @Column(nullable = true, unique = false)
    public Boolean writepermission;

    /** idletimeプロパティ */
    @Column(precision = 10, nullable = true, unique = false)
    public Integer idletime;

    /** uploadrateプロパティ */
    @Column(precision = 10, nullable = true, unique = false)
    public Integer uploadrate;

    /** downloadrateプロパティ */
    @Column(precision = 10, nullable = true, unique = false)
    public Integer downloadrate;

    /** maxloginnumberプロパティ */
    @Column(precision = 10, nullable = true, unique = false)
    public Integer maxloginnumber;

    /** maxloginperipプロパティ */
    @Column(precision = 10, nullable = true, unique = false)
    public Integer maxloginperip;

    @Version
    @Column(precision = 64, nullable = false, unique = false)
   public Long version = 0L;
}