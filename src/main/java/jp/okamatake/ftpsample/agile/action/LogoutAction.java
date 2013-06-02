package jp.okamatake.ftpsample.agile.action;

import javax.annotation.Resource;

import jp.okamatake.ftpsample.agile.form.LogoutForm;

import org.seasar.framework.aop.annotation.RemoveSession;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

public class LogoutAction {

	@ActionForm
	@Resource(name="logoutForm")
	protected LogoutForm form;

	@Execute(validator = false)
	@RemoveSession(name = "userDto")
	public String index() {
		return "/?redirect=true";
	}
}
