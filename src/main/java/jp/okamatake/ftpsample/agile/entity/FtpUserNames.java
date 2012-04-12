package jp.okamatake.ftpsample.agile.entity;

import javax.annotation.Generated;
import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link FtpUser}のプロパティ名の集合です。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.45", "org.seasar.extension.jdbc.gen.internal.model.NamesModelFactoryImpl"}, date = "2012/04/12 10:28:04")
public class FtpUserNames {

    /**
     * useridのプロパティ名を返します。
     * 
     * @return useridのプロパティ名
     */
    public static PropertyName<String> userid() {
        return new PropertyName<String>("userid");
    }

    /**
     * userpasswordのプロパティ名を返します。
     * 
     * @return userpasswordのプロパティ名
     */
    public static PropertyName<String> userpassword() {
        return new PropertyName<String>("userpassword");
    }

    /**
     * homedirectoryのプロパティ名を返します。
     * 
     * @return homedirectoryのプロパティ名
     */
    public static PropertyName<String> homedirectory() {
        return new PropertyName<String>("homedirectory");
    }

    /**
     * enableflagのプロパティ名を返します。
     * 
     * @return enableflagのプロパティ名
     */
    public static PropertyName<Byte> enableflag() {
        return new PropertyName<Byte>("enableflag");
    }

    /**
     * writepermissionのプロパティ名を返します。
     * 
     * @return writepermissionのプロパティ名
     */
    public static PropertyName<Byte> writepermission() {
        return new PropertyName<Byte>("writepermission");
    }

    /**
     * idletimeのプロパティ名を返します。
     * 
     * @return idletimeのプロパティ名
     */
    public static PropertyName<Integer> idletime() {
        return new PropertyName<Integer>("idletime");
    }

    /**
     * uploadrateのプロパティ名を返します。
     * 
     * @return uploadrateのプロパティ名
     */
    public static PropertyName<Integer> uploadrate() {
        return new PropertyName<Integer>("uploadrate");
    }

    /**
     * downloadrateのプロパティ名を返します。
     * 
     * @return downloadrateのプロパティ名
     */
    public static PropertyName<Integer> downloadrate() {
        return new PropertyName<Integer>("downloadrate");
    }

    /**
     * maxloginnumberのプロパティ名を返します。
     * 
     * @return maxloginnumberのプロパティ名
     */
    public static PropertyName<Integer> maxloginnumber() {
        return new PropertyName<Integer>("maxloginnumber");
    }

    /**
     * maxloginperipのプロパティ名を返します。
     * 
     * @return maxloginperipのプロパティ名
     */
    public static PropertyName<Integer> maxloginperip() {
        return new PropertyName<Integer>("maxloginperip");
    }

    /**
     * @author S2JDBC-Gen
     */
    public static class _FtpUserNames extends PropertyName<FtpUser> {

        /**
         * インスタンスを構築します。
         */
        public _FtpUserNames() {
        }

        /**
         * インスタンスを構築します。
         * 
         * @param name
         *            名前
         */
        public _FtpUserNames(final String name) {
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
        public _FtpUserNames(final PropertyName<?> parent, final String name) {
            super(parent, name);
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
         * userpasswordのプロパティ名を返します。
         *
         * @return userpasswordのプロパティ名
         */
        public PropertyName<String> userpassword() {
            return new PropertyName<String>(this, "userpassword");
        }

        /**
         * homedirectoryのプロパティ名を返します。
         *
         * @return homedirectoryのプロパティ名
         */
        public PropertyName<String> homedirectory() {
            return new PropertyName<String>(this, "homedirectory");
        }

        /**
         * enableflagのプロパティ名を返します。
         *
         * @return enableflagのプロパティ名
         */
        public PropertyName<Byte> enableflag() {
            return new PropertyName<Byte>(this, "enableflag");
        }

        /**
         * writepermissionのプロパティ名を返します。
         *
         * @return writepermissionのプロパティ名
         */
        public PropertyName<Byte> writepermission() {
            return new PropertyName<Byte>(this, "writepermission");
        }

        /**
         * idletimeのプロパティ名を返します。
         *
         * @return idletimeのプロパティ名
         */
        public PropertyName<Integer> idletime() {
            return new PropertyName<Integer>(this, "idletime");
        }

        /**
         * uploadrateのプロパティ名を返します。
         *
         * @return uploadrateのプロパティ名
         */
        public PropertyName<Integer> uploadrate() {
            return new PropertyName<Integer>(this, "uploadrate");
        }

        /**
         * downloadrateのプロパティ名を返します。
         *
         * @return downloadrateのプロパティ名
         */
        public PropertyName<Integer> downloadrate() {
            return new PropertyName<Integer>(this, "downloadrate");
        }

        /**
         * maxloginnumberのプロパティ名を返します。
         *
         * @return maxloginnumberのプロパティ名
         */
        public PropertyName<Integer> maxloginnumber() {
            return new PropertyName<Integer>(this, "maxloginnumber");
        }

        /**
         * maxloginperipのプロパティ名を返します。
         *
         * @return maxloginperipのプロパティ名
         */
        public PropertyName<Integer> maxloginperip() {
            return new PropertyName<Integer>(this, "maxloginperip");
        }
    }
}