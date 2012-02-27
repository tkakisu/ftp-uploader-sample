package jp.okamatake.ftpsample.agile.service;

import java.util.List;

import javax.annotation.Resource;

import jp.okamatake.ftpsample.agile.dto.ImageListDto;

import org.seasar.extension.jdbc.JdbcManager;

public class ImageListService {

	@Resource
	private JdbcManager jdbcManager;
	
	public List<ImageListDto> getImageList(String userid) {
		return jdbcManager.selectBySql(ImageListDto.class, "select image_id from ftp_image where userid = ? order by image_id", userid).getResultList();
	}
}
