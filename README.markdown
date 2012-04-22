# JDBC-FTP Server Sample

Apache FTP Serverで、ファイルシステムの代わりにRDBに使用するサンプル実装です。
また、SAStrutsの設定を連動させるため、SAStruts用のサンプル実装と同じシステムになっています。

アカウントは test/passwordです。

ユーザーテーブルの実装は下記の通りです。
http://mina.apache.org/ftpserver/database-user-manager.html

* ディレクトリ（ないしタグ）、パーミッションは未実装です。
* STOR、LIST、DELE以外のFTPコマンドは未実装です。

## Required

* Java SE 7
* Maven 2以上
* Tomcat6、Grassfish等のServletコンテナ
* MySQL5

## Instration

※RDB上のユーザー/パスワード/DB名を変更する場合、 src/main/resources/jdbc.dicon を合わせて修正してください。

* MySQLサーバに、ftpsampleユーザを作る（パスワードはftpsampleを想定。）
* MySQLサーバに、ftpsample DBを作る
* ftpsampleユーザにftpsample DBの全権限を付与
* mvn antrun:run -Dant.target=migrate でftpsample DB上にテーブルとデータを作る。
* mvn war:war でwarファイルを作る。
