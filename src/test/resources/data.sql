-- create 2013-01-11 by mg

delete from todoitem;
delete from todolist;
delete from authority;
delete from persistent_login;
delete from scheduled_password_reset_mail;
delete from scheduled_registration_mail;
delete from user;

-- Username/Password alice/alice
insert into user (user_id, password) values ('alice@nowhere.tld', '93fd4fa4d6a33d525c78eaa1abc7c0d080864c76b6ac8d09c8ae33dc0b2ef3f971f559fb696b8bf7');
insert into authority (user_id, authority) values ('alice@nowhere.tld', 'ROLE_USER');

insert into todolist (user_id, listName) values ('alice@nowhere.tld', 'eins');
insert into todolist (user_id, listName) values ('alice@nowhere.tld', 'zwei');

insert into todoitem (todolist_id, itemname) values (1, 'Suppe kaufen');
insert into todoitem (todolist_id, itemname) values (1, 'Fische braten');
insert into todoitem (todolist_id, itemname) values (1, 'Muscheln waessern');
insert into todoitem (todolist_id, itemname) values (2, 'Passwortraten');

insert into user (user_id, password, enabled, registrationtoken) values ('new@registered.tld', 'secret', 0, 'registration-token');
insert into authority (user_id, authority) values ('new@registered.tld', 'ROLE_USER');

-- EOF
