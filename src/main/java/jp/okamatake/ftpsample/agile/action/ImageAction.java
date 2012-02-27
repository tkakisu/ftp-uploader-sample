package jp.okamatake.ftpsample.agile.action;

import java.io.BufferedOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jp.okamatake.ftpsample.agile.dto.UserDto;
import jp.okamatake.ftpsample.agile.entity.FtpImage;
import jp.okamatake.ftpsample.agile.form.ImageForm;
import jp.okamatake.ftpsample.agile.service.FtpImageService;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

public class ImageAction {
	
	@ActionForm
	@Resource(name = "imageForm")
	protected ImageForm form;
	
	@Resource
	protected UserDto userDto;
	
	@Resource
	protected HttpServletResponse response;

	@Resource
	protected FtpImageService ftpImageService;
	
	@Execute(validator = false, urlPattern = "{fileid}")
	public String downloadImage() {
		FtpImage image = ftpImageService.findById(form.fileid);
		
		response.setContentType("image/jpeg");
		try (BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {
			out.write(image.image);
			out.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
