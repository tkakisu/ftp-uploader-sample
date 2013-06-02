package jp.okamatake.ftpsample.agile.form;

import org.seasar.struts.annotation.Required;

/**
 * @author okamoto
 */
public class LoginForm {

	@Required(target = "doLogin")
	public String username;

	@Required(target = "doLogin")
	public String password;
}
