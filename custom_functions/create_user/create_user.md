``` mysql
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_user`(uid varchar(255), uname varchar(20), ugender varchar(5), uavatar varchar(255))
begin declare flag int default 0;
	declare ln varchar(255);
	declare labelNames cursor for (select label_name from label);
	declare continue handler for not found set flag = 1;
	open labelNames;
		insert into user(_id,author_name,gender,avatar, create_time) values (uid,uname,ugender,uavatar, NOW());
		FETCH labelNames into ln;
		while flag != 1 do
			insert into user_label(user_id, label_name) values (uid, ln);
			FETCH labelNames into ln;
		end while;
	close labelNames;
end
```