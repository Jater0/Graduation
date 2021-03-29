``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `create_article_user`(`art_id` varchar(255), `u_id` varchar(255), `header` varchar(255), `type` varchar(255), `content_url` varchar(255)) RETURNS int
BEGIN
	declare _err int default 0;
	declare now_time TIMESTAMP default NOW();
	declare u_name varchar(255);
	declare u_avatar varchar(255);
	declare continue handler for SQLEXCEPTION, SQLWARNING, NOT FOUND set _err = 1;
	select author_name, avatar into u_name, u_avatar from user where _id = u_id;
	if _err = 1 then 
		RETURN 0;
	else
		insert into article(_id, topic_id, `mode`, author_id, author_name, avatar, `status`, classify, title, content, create_time) 
			values (art_id, art_id, "article", u_id, u_name, u_avatar, "normal", type, header, content_url, now_time);
		insert into topic(_id, author_id, author_name, avatar, `mode`, title, classify, create_time)
			values (art_id, u_id, u_name, u_avatar, "article", header, type, now_time);
		update user set integral_count = integral_count + 2 where _id = u_id;
		return 1;
	end if;
END
```