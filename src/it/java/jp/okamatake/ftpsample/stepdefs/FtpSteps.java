package jp.okamatake.ftpsample.stepdefs;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import cucumber.api.java.ja.ならば;
import cucumber.api.java.ja.もし;

public class FtpSteps {

	private static final Pattern PORT_PATTERN = Pattern.compile(".+?(\\d+,\\d+,\\d+,\\d+),(\\d+),(\\d+)");

	private final FtpConnector ftpConnector;

	public FtpSteps(FtpConnector ftpConnector) {
		this.ftpConnector = ftpConnector;
	}

	@もし("^サーバーにポート(\\d+)で接続する$")
	public void サーバーに接続する(int port) throws Throwable {
		ftpConnector.connect(port);
	}

	@もし("^\"([^\"]*)\"を送信する$")
	public void コマンドを送信する(String command) throws Throwable {
		ftpConnector.send(command);
	}

	@ならば("^\"([^\"]*)\"が返信されること$")
	public void メッセージが返信されること(String message) throws Throwable {
		String actual = ftpConnector.latestMessage;
		assertThat(actual, startsWith(message));
	}

	@ならば("^\"([^\"]*)\"と、データコネクション情報が返信されること$")
	public void PASVが返信されること(String status) throws Throwable {
		String actual = ftpConnector.latestMessage;
		assertThat(actual, startsWith(status));

		Matcher matcher = PORT_PATTERN.matcher(actual);
		if (matcher.find()) {
			String address = matcher.group(1).replace(',', '.');

			List<String> portChunks = new ArrayList<>(2);
			for (int i = 2; i <= 3; i++) {
				portChunks.add(matcher.group(i));
			}

			ftpConnector.setDataPort(address, portChunks);
		}
	}

	@もし("^\"([^\"]*)\"をアップロードする$")
	public void ファイルをアップロードする(String fileName) throws Throwable {
		try (InputStream resource = getClass().getClassLoader().getResourceAsStream(fileName);) {
			ftpConnector.upload(resource);
		}
	}
}
