-- created 2013-01-11 by mg

create table users (
  email             varchar_ignorecase(128) not null primary key,
  password          varchar_ignorecase(80)  not null,
  enabled           boolean                 not null default 1,
  registrationtoken varchar(128)            default null
);

create table authorities (
  email     varchar_ignorecase(128) not null,
  authority varchar_ignorecase(50)  not null
);

alter table authorities add foreign key (email) references users (email)
  on delete cascade;

create unique index idx_authorities_email on authorities (email, authority);

create table persistent_logins (
  username  varchar(128) not null,
  series    varchar(64)  primary key,
  token     varchar(64)  not null,
  last_used timestamp not null
);

alter table persistent_logins add foreign key (username) references users (email)
  on delete cascade;

create table scheduled_registration_mail (
  id            bigint not null primary key auto_increment,
  email         varchar_ignorecase(128) not null,
  activationurl varchar(1024) not null,
  attempts      int not null default 1,
  lastattempt   timestamp not null default now()
);

alter table scheduled_registration_mail add foreign key (email) references users (email)
  on delete cascade;

create table todolist (
  id       bigint not null primary key auto_increment,
  user     varchar_ignorecase(50) not null,
  listname varchar(64) not null,
  created  timestamp not null default now()
);

alter table todolist add foreign key (user) references users (email);

create unique index idx_todolist_user_listname on todolist (user, listname);

create table todoitem (
  id bigint not null primary key auto_increment,
  todolist_id bigint not null,
  itemname varchar(64) not null,
  created timestamp not null default now(),
  done boolean not null default 0
);

alter table todoitem add foreign key (todolist_id) references todolist (id);

-- EOF
