``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `update_browse_author`
	(`uid` varchar(255), `aid` varchar(255), `mm` int, `bro` int) RETURNS int
BEGIN
	declare isExist int;
	declare _err int default 0;
	declare continue handler for SQLEXCEPTION, SQLWARNING, NOT FOUND set _err = 1;
	select count(*) into isExist from browse_author 
		where user_id = uid and author_id = aid and `month` = mm;
	if _err = 1 then
		return -1;
	else 
		if isExist = 0 then
			insert into browse_author(user_id, author_id, `month`, browse) values (uid, aid, mm, bro);
			return 0;
		else
			update browse_author set browse = browse + bro 
				where user_id = uid and author_id = aid and `month` = mm;
			return 1;
		end if;
	end if;
END
```