package jp.okamatake.ftpsample.agile.entity;

import java.math.BigInteger;
import java.sql.Timestamp;
import javax.annotation.Generated;
import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link FtpImage}のプロパティ名の集合です。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.45", "org.seasar.extension.jdbc.gen.internal.model.NamesModelFactoryImpl"}, date = "2013/06/24 1:39:47")
public class FtpImageNames {

    /**
     * imageIdのプロパティ名を返します。
     * 
     * @return imageIdのプロパティ名
     */
    public static PropertyName<BigInteger> imageId() {
        return new PropertyName<BigInteger>("imageId");
    }

    /**
     * useridのプロパティ名を返します。
     * 
     * @return useridのプロパティ名
     */
    public static PropertyName<String> userid() {
        return new PropertyName<String>("userid");
    }

    /**
     * filenameのプロパティ名を返します。
     * 
     * @return filenameのプロパティ名
     */
    public static PropertyName<String> filename() {
        return new PropertyName<String>("filename");
    }

    /**
     * imageのプロパティ名を返します。
     * 
     * @return imageのプロパティ名
     */
    public static PropertyName<byte[]> image() {
        return new PropertyName<byte[]>("image");
    }

    /**
     * updatedDateのプロパティ名を返します。
     * 
     * @return updatedDateのプロパティ名
     */
    public static PropertyName<Timestamp> updatedDate() {
        return new PropertyName<Timestamp>("updatedDate");
    }

    /**
     * @author S2JDBC-Gen
     */
    public static class _FtpImageNames extends PropertyName<FtpImage> {

        /**
         * インスタンスを構築します。
         */
        public _FtpImageNames() {
        }

        /**
         * インスタンスを構築します。
         * 
         * @param name
         *            名前
         */
        public _FtpImageNames(final String name) {
            super(name);
        }

        /**
         * インスタンスを構築します。
         * 
         * @param parent
         *            親
         * @param name
         *            名前
         */
        public _FtpImageNames(final PropertyName<?> parent, final String name) {
            super(parent, name);
        }

        /**
         * imageIdのプロパティ名を返します。
         *
         * @return imageIdのプロパティ名
         */
        public PropertyName<BigInteger> imageId() {
            return new PropertyName<BigInteger>(this, "imageId");
        }

        /**
         * useridのプロパティ名を返します。
         *
         * @return useridのプロパティ名
         */
        public PropertyName<String> userid() {
            return new PropertyName<String>(this, "userid");
        }

        /**
         * filenameのプロパティ名を返します。
         *
         * @return filenameのプロパティ名
         */
        public PropertyName<String> filename() {
            return new PropertyName<String>(this, "filename");
        }

        /**
         * imageのプロパティ名を返します。
         *
         * @return imageのプロパティ名
         */
        public PropertyName<byte[]> image() {
            return new PropertyName<byte[]>(this, "image");
        }

        /**
         * updatedDateのプロパティ名を返します。
         *
         * @return updatedDateのプロパティ名
         */
        public PropertyName<Timestamp> updatedDate() {
            return new PropertyName<Timestamp>(this, "updatedDate");
        }
    }
}