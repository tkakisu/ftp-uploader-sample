package jp.okamatake.ftpsample.stepdefs;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import cucumber.api.DataTable;
import cucumber.api.java.ja.ならば;
import cucumber.api.java.ja.もし;

public class WebSteps {

	private final WebConnector webConnector;

	public WebSteps(WebConnector webConnector) {
		this.webConnector = webConnector;
	}

	@もし("^\"([^\"]*)\"を表示する$")
	public void 画面を表示する(String url) throws Throwable {
		webConnector.open(url);
	}

	@もし("^\"([^\"]*)\"に\"([^\"]*)\"を入力する$")
	public void 入力する(String name, String text) throws Throwable {
		webConnector.inputText(name, text);
	}

	@もし("^\"([^\"]*)\"をクリックする$")
	public void クリックする(String selector) throws Throwable {
		webConnector.click(selector);
	}

	@もし("^以下の情報を入力する:$")
	public void 以下の情報を入力する(DataTable table) throws Throwable {
		for (InputValue inputValue : table.<InputValue>asList(InputValue.class)) {
			入力する(inputValue.name, inputValue.value);
		}
	}

	@ならば("^\"([^\"]*)\"に遷移すること$")
	public void 遷移する(String url) throws Throwable {
		assertThat(webConnector.getCurrentUrl(), is(url));
	}

	@ならば("^\"([^\"]*)\"の画像が表示されていること$")
	public void 表示されている(String xpathExpression) throws Throwable {
		assertThat(webConnector.existsXpath(xpathExpression), is(true));
	}

	public static class InputValue {
		public String name;
		public String value;
	}
}
