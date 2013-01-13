-- created 2013-01-11 by mg

create table users (
  username varchar_ignorecase(50) not null primary key,
  password varchar_ignorecase(80) not null,
  enabled boolean not null
);

create table authorities (
  username varchar_ignorecase(50) not null,
  authority varchar_ignorecase(50) not null,
  constraint fk_authorities_users foreign key (username) references users (username)
);

create unique index idx_authorities_username on authorities (username, authority);

create table persistent_logins (
  username varchar(64) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
);

create table todolist (
  id bigint not null primary key auto_increment,
  username varchar_ignorecase(50) not null,
  listname varchar(64) not null,
  created timestamp not null default now(),
  constraint fk_todolist_users foreign key (username) references users (username)
);

create unique index idx_todolist_username_listname on todolist (username, listname);

create table todoitem (
  id bigint not null primary key auto_increment,
  todolist_id bigint not null,
  itemname varchar(64) not null,
  created timestamp not null default now(),
  done boolean not null default 0
);

alter table todoitem add foreign key (todolist_id) references todolist (id);

-- EOF
