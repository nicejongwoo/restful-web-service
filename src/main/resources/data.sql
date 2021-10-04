insert into user values(90001, sysdate(), 'user1', 'pass1', '701010-1111111');
insert into user values(90002, sysdate(), 'user2', 'pass2', '801010-2222222');
insert into user values(90003, sysdate(), 'user3', 'pass3', '901010-1111111');

insert into post values(10001, 'Test Post 1', 90001);
insert into post values(10002, 'Test Post 2', 90001);
insert into post values(10003, 'Test Post 3', 90002);
