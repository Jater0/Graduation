``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `create_topic`(
    `tid` varchar(255),`aid` varchar(255),
    `header` varchar(255),`type` varchar(255),`ctime` timestamp) RETURNS int
begin
	declare aname varchar(255);
	declare aavatar varchar(255);
	declare _err int default 0;
	declare continue handler for SQLEXCEPTION, SQLWARNING, NOT FOUND set _err = 1;
	select author_name, avatar into aname, aavatar from user where _id = aid;
	if _err = 1 then
		return 0;
	else
		insert into topic_and_article
			(_id, author_id, author_name, avatar, `mode`, title, classify, create_time) 
			values 
			(tid, aid, aname, aavatar, "topic", header, type, ctime);
		update user set integral_count = integral_count + 1 where _id = aid;
		return 1;
	end if;
end
```