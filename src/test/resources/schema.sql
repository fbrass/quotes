-- created 2013-01-11 by mg

create table user (
  id bigint not null auto_increment primary key,
  email varchar(128) not null,
  crypted_password varchar(64) not null
);

create unique index user_email on user (email);

-- EOF
