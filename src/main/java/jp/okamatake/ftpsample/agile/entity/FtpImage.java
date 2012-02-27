package jp.okamatake.ftpsample.agile.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * FtpImageエンティティクラス
 * 
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "userid", "filename" }) })
@Generated(value = {"S2JDBC-Gen 2.4.45", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl"}, date = "2012/04/08 13:05:25")
public class FtpImage implements Serializable {

    private static final long serialVersionUID = 1L;

    /** imageIdプロパティ */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(precision = 20, nullable = false, unique = true)
    public BigInteger imageId;

    /** useridプロパティ */
    @Column(length = 64, nullable = false, unique = false)
    public String userid;

    /** filenameプロパティ */
    @Column(length = 255, nullable = false, unique = false)
    public String filename;

    /** imageプロパティ */
    @Lob
    @Column(length = 2147483647, nullable = true, unique = false)
    public byte[] image;

    /** updatedDateプロパティ */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, unique = false)
    public Timestamp updatedDate;
}