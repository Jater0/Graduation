``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `answer_topic_admin`(`art_id` varchar(255), `tpc_id` varchar(255), `u_id` varchar(255), `u_name` varchar(255), `u_avatar` varchar(255), `header` varchar(255), `type` varchar(255), `content_url` varchar(255)) RETURNS int
BEGIN
	declare _err int default 0;
	declare now_time TIMESTAMP default NOW();
	declare continue handler for SQLEXCEPTION, SQLWARNING, NOT FOUND set _err = 1;
	if _err = 1 then
	return 0;
	else
		insert into article(_id, topic_id, `mode`, author_id, author_name, avatar, `status`, classify, title, content, create_time) 
		values (art_id, tpc_id, "topic", u_id, u_name, u_avatar, "normal", type, header, content_url, now_time);
		RETURN 1;
	end if;
END
```