package jp.okamatake.ftpsample.agile.entity;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
    @Column(precision = 3, nullable = true, unique = false)
    public Byte enableflag;

    /** writepermissionプロパティ */
    @Column(precision = 3, nullable = true, unique = false)
    public Byte writepermission;

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
}