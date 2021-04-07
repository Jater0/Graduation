``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `answer_topic`(
    `art_id` varchar(255), `tpc_id` varchar(255), 
    `u_id` varchar(255), `header` varchar(255), 
    `type` varchar(255), `content_url` varchar(255)) RETURNS int
BEGIN
	declare _err int default 0;
	declare u_name varchar(255);
	declare u_avatar varchar(255);
	declare continue handler for SQLEXCEPTION, SQLWARNING, NOT FOUND set _err = 1;
	select author_name, avatar into u_name, u_avatar from user where _id = u_id;
	if _err = 1 then
		return 0;
	else
		insert into article
            (_id, father_id, `mode`, author_id, author_name, 
             avatar, classify, title, content, create_time)
            values 
            (art_id, tpc_id, "topic", u_id, u_name, 
            u_avatar, type, header, content_url, NOW());
		update user set integral_count = integral_count + 2 where _id = u_id;
		return 1;
	end if;
END
```