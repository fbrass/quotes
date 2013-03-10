-- create 2013-01-11 by mg

delete from todoitem;
delete from todolist;
delete from authorities;
delete from users;

-- Username/Password alice/alice
insert into users (email, password) values ('alice@nowhere.tld', '93fd4fa4d6a33d525c78eaa1abc7c0d080864c76b6ac8d09c8ae33dc0b2ef3f971f559fb696b8bf7');
insert into authorities (email, authority) values ('alice@nowhere.tld', 'ROLE_USER');

insert into todolist (user, listName) values ('alice@nowhere.tld', 'eins');
insert into todolist (user, listName) values ('alice@nowhere.tld', 'zwei');

insert into todoitem (todolist_id, itemname) values (1, 'Suppe kaufen');
insert into todoitem (todolist_id, itemname) values (1, 'Fische braten');
insert into todoitem (todolist_id, itemname) values (1, 'Muscheln waessern');
insert into todoitem (todolist_id, itemname) values (2, 'Passwortraten');

insert into users (email, password, enabled, registrationtoken) values ('new@registered.tld', 'secret', 0, 'registration-token');
insert into authorities (email, authority) values ('new@registered.tld', 'ROLE_USER');

-- EOF
