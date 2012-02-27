package jp.okamatake.ftpsample.agile.action;

import javax.annotation.Resource;

import jp.okamatake.ftpsample.agile.dto.UserDto;
import jp.okamatake.ftpsample.agile.form.LoginForm;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

public class LoginAction {
	
	@ActionForm
	@Resource(name="loginForm")
	protected LoginForm form;
	
	@Resource
	protected UserDto userDto;
	
	@Execute(validator = false)
	public String index() {
		return "login.jsp";
	}

	@Execute(validator = false, redirect = true)
	public String doLogin() {
		// XXX: do auth
		
		userDto.userid = form.username;
		return "/home";
	}
}
