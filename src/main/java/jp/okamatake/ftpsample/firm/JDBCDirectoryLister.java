package jp.okamatake.ftpsample.firm;

import java.io.IOException;
import java.util.List;

import jp.okamatake.ftpsample.agile.entity.FtpImage;
import jp.okamatake.ftpsample.agile.service.FtpImageService;

import org.apache.ftpserver.command.impl.listing.ListArgument;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.util.DateUtils;
import org.seasar.framework.container.SingletonS2Container;

/**
 * @author okamoto
 * 
 */
public class JDBCDirectoryLister {

	private final static char DELIM = ' ';

	private final static char[] NEWLINE = { '\r', '\n' };
	
	public String listFiles(final ListArgument argument, FtpIoSession session) throws IOException {    	
		FtpImageService ftpImageService = SingletonS2Container.getComponent(FtpImageService.class);
		List<FtpImage> ftpImages = ftpImageService.findByUserId(session.getUser().getName());
		
		return traverseFiles(ftpImages);
    }

	private String traverseFiles(final List<FtpImage> images) {
		StringBuilder sb = new StringBuilder();
		for (FtpImage image : images) {
			if (image == null) {
				continue;
			}

			sb.append(format(image));
		}

		return sb.toString();
	}

	private String format(FtpImage image) {
        StringBuilder sb = new StringBuilder();
        sb.append("-rw-------");
        sb.append(DELIM);
        sb.append(DELIM);
        sb.append(DELIM);
        sb.append("1");
        sb.append(DELIM);
        sb.append(image.userid);
        sb.append(DELIM);
        sb.append(image.userid);
        sb.append(DELIM);
        sb.append(image.image.length);
        sb.append(DELIM);
        sb.append(DateUtils.getUnixDate(image.updatedDate.getTime()));
        sb.append(DELIM);
        sb.append(image.filename);
        sb.append(NEWLINE);

        return sb.toString();
    }
}
