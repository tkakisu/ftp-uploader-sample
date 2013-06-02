package jp.okamatake.ftpsample.agile.action;

import javax.annotation.Resource;

import jp.okamatake.ftpsample.agile.dto.UserDto;
import jp.okamatake.ftpsample.agile.form.LoginForm;
import jp.okamatake.ftpsample.agile.service.FtpUserService;

import org.seasar.framework.aop.annotation.RemoveSession;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.exception.ActionMessagesException;

public class LoginAction {

	@ActionForm
	@Resource(name="loginForm")
	protected LoginForm form;

	@Resource
	protected UserDto userDto;

	@Resource
	protected FtpUserService ftpUserService;

	@RemoveSession(name = "userDto")
	@Execute(validator = false)
	public String index() {
		return "login.jsp";
	}

	@Execute(input = "login.jsp", redirect = true)
	public String doLogin() {
		authenticate(form.username, form.password);

		return "/home";
	}

	protected void authenticate(String userName, String password) {
		if (ftpUserService.authenticate(userName, password) == null) {
			throw new ActionMessagesException("errors.auth.fail");
		}

		userDto.userid = userName;
	}
}
