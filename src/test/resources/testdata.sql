-- create 2013-01-11 by mg

delete from todoitem;
delete from todolist;
delete from authorities;
delete from users;

-- Username/Password alice/alice
insert into users (username, password, enabled) values ('alice', '93fd4fa4d6a33d525c78eaa1abc7c0d080864c76b6ac8d09c8ae33dc0b2ef3f971f559fb696b8bf7', 1);
insert into authorities (username, authority) values ('alice', 'ROLE_USER');

-- insert into users (username, password, enabled) values ('bob', 'secret', 1);
-- insert into authorities (username, authority) values ('bob', 'ROLE_USER');

insert into todolist (username, listName) values ('alice', 'eins');
insert into todolist (username, listName) values ('alice', 'zwei');


insert into todoitem (todolist_id, itemname) values (1, 'Suppe kaufen');
insert into todoitem (todolist_id, itemname) values (1, 'Fische braten');
insert into todoitem (todolist_id, itemname) values (1, 'Muscheln waessern');
insert into todoitem (todolist_id, itemname) values (2, 'Passwortraten');

-- EOF
