# language: ja

フィーチャ: FTPのPassive画像送信
  DB上のユーザー/パスワードを元に平文で認証し、Passiveモードで画像ファイルを追加できること。
  なぜなら、汎用機からのFTP送信を正しく処理するため
  コマンドのレスポンスについては http://www.ietf.org/rfc/rfc959.txt を参照。

シナリオ: PassiveモードによるSTORでのバイナリデータ追加
  前提 サーバーにポート21で接続する
  かつ "USER test"を送信する
  かつ "PASS password"を送信する

  もし "TYPE I"を送信する
  ならば "200"が返信されること

  もし "PASV"を送信する
  ならば "227"と、データコネクション情報が返信されること
  
  もし "STOR test_001.png"を送信する
  ならば "150"が返信されること
  もし "jp/okamatake/ftpsample/test1/test_001.png"をアップロードする
  ならば "226"が返信されること

  もし "QUIT"を送信する
  ならば "221"が返信されること

  もし "http://localhost:8080/login/"を表示する
  かつ 以下の情報を入力する:
  |name    |value   |
  |username|test    |
  |password|password|
  かつ "#login>[value=ログイン]"をクリックする
  ならば "http://localhost:8080/home/"に遷移すること
  かつ "//img[@alt='test_001.pngの画像']"の画像が表示されていること

シナリオ: STORでのバイナリデータ上書き
  前提 サーバーにポート21で接続する
  かつ "USER test"を送信する
  かつ "PASS password"を送信する

  もし "TYPE I"を送信する
  ならば "200"が返信されること

  もし "PASV"を送信する
  ならば "227"と、データコネクション情報が返信されること
  
  もし "STOR test_001.png"を送信する
  ならば "150"が返信されること
  もし "jp/okamatake/ftpsample/test2/test_001.png"をアップロードする
  ならば "226"が返信されること

  もし "QUIT"を送信する
  ならば "221"が返信されること
