package jp.okamatake.ftpsample.agile.action;

import javax.annotation.Resource;

import jp.okamatake.ftpsample.agile.form.IndexForm;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

public class IndexAction {
	@ActionForm
	@Resource(name = "indexForm")
	protected IndexForm form;

	@Execute(validator = false)
	public String index() {
		return "index.jsp";
	}
}
