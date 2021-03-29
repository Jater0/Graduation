``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `create_article_admin`(`aid` varchar(255), `admid` varchar(255), `admname` varchar(255), `admavatar` varchar(255), `header` varchar(255), `type` varchar(255), `content_url` varchar(255)) RETURNS int
BEGIN
	declare _err int default 0;
	declare now_time TIMESTAMP default NOW();
	declare continue handler for SQLEXCEPTION, SQLWARNING, NOT FOUND set _err = 1;
	if _err = 1 then
	return 0;
	else
		insert into article(_id, topic_id, `mode`, author_id, author_name, avatar, `status`, classify, title, content, create_time) 
		values (aid, aid, "article", admid, admname, admavatar, "normal", type, header, content_url, now_time);
		insert into topic(_id, author_id, author_name, avatar, `mode`, title, classify, create_time)
		values (aid, admid, admname, admavatar, "article", header, type, now_time);
		RETURN 1;
	end if;
END
```