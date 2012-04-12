create table FTP_IMAGE (
    IMAGE_ID bigint not null auto_increment,
    UPDATED_DATE timestamp not null,
    IMAGE longblob,
    FILENAME varchar(255) not null,
    USERID varchar(64) not null,
    constraint FTP_IMAGE_PK primary key(IMAGE_ID)
);
