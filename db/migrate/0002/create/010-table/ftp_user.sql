create table FTP_USER (
    USERID varchar(64) not null,
    VERSION bigint not null,
    MAXLOGINPERIP int,
    MAXLOGINNUMBER int,
    DOWNLOADRATE int,
    UPLOADRATE int,
    IDLETIME int,
    WRITEPERMISSION boolean,
    ENABLEFLAG boolean,
    HOMEDIRECTORY varchar(45),
    USERPASSWORD varchar(64) not null,
    constraint FTP_USER_PK primary key(USERID)
);
