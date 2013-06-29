select
	image_id,
	filename
from
	ftp_image
where
	userid = ?
order by
	image_id
