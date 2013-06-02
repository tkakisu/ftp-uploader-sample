package jp.okamatake.ftpsample.agile.action;

import java.util.List;

import javax.annotation.Resource;

import jp.okamatake.ftpsample.agile.dto.ImageListDto;
import jp.okamatake.ftpsample.agile.dto.UserDto;
import jp.okamatake.ftpsample.agile.form.HomeForm;
import jp.okamatake.ftpsample.agile.service.ImageListService;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * @author okamoto
 *
 */
public class HomeAction {
	
	public List<ImageListDto> imageList;
	
	@ActionForm
	@Resource(name = "homeForm")
	protected HomeForm form;

	@Resource
	protected ImageListService imageListService;
	
	@Resource
	protected UserDto userDto;
	
	@Execute(validator = false)
	public String index() {
		if (userDto == null || userDto.userid == null) {
			return "/login?redirect=true";
		}
		
		imageList = imageListService.getImageList(userDto.userid);

		return "home.jsp";
	}
}
