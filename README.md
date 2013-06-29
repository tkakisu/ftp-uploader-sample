# JDBC-FTP Server Sample

Apache FTP Serverを使った、RDBベースのFTPサンプル実装です。
また、SAStrutsと連動して、アップロードした画像をWebで表示できるようにしています。

アカウントは test/password です。
※RDB上のユーザー/パスワード/DB名を変更する場合、 src/main/resources/jdbc.dicon を合わせて修正してください。

ユーザーテーブルの実装は下記を踏襲しています。
http://mina.apache.org/ftpserver/database-user-manager.html

* ディレクトリ（ないしタグ）、パーミッションは未実装です。
* STOR、LIST、DELE以外のFTPコマンドは未実装です。

## Required

* Java SE 7
* Maven 3以上
* MySQL5

## Instration

* MySQLサーバに、ftpsampleユーザを作る（パスワードはftpsampleを想定。）
* MySQLサーバに、ftpsample DBを作る
* ftpsampleユーザにftpsample DBの全権限を付与
* mvn antrun:run -Dant.target=migrate でftpsample DB上にテーブルとデータを作る。

## Execution

mvn jetty:run で簡易的な実行が可能です。
http://loalhost:8080/ でアクセス出来ます。

任意のServletコンテナから実行する場合は、 mvn war:war もしくは mvn verify でwarファイルを作成してください。

## Integration Test

mvn verify でJetty上でIntegration Testを実施します。