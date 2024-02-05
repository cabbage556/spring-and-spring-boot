insert into user_details (id, birth_date, name)
values (1000, current_date(), 'Kim');

insert into user_details (id, birth_date, name)
values (1001, current_date(), 'Tak');

insert into user_details (id, birth_date, name)
values (1002, current_date(), 'Choi');

insert into post (id, description, user_id)
values (2000, 'Learn AWS', 1000);

insert into post (id, description, user_id)
values (2001, 'Learn DevOps', 1001);

insert into post (id, description, user_id)
values (2002, 'Get AWS Certified', 1002);
