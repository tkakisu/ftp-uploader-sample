create table FTP_USER (
    USERID varchar(64) not null,
    MAXLOGINPERIP int,
    MAXLOGINNUMBER int,
    DOWNLOADRATE int,
    UPLOADRATE int,
    IDLETIME int,
    WRITEPERMISSION smallint,
    ENABLEFLAG smallint,
    HOMEDIRECTORY varchar(45),
    USERPASSWORD varchar(64) not null,
    constraint FTP_USER_PK primary key(USERID)
);
