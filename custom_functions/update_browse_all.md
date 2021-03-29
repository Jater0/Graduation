``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `update_browse_all`(`uid` varchar(255), `mm` int, `browse` int) RETURNS int
BEGIN
	declare isExist int;
	select count(*) into isExist from browse_all where user_id = uid and `month` = mm;
	if isExist = 0 then
		insert into browse_all(user_id, `month`, browse_all) values (uid, mm, browse);
		return 0;
	else
		update browse_all set browse_all = browse_all + browse where user_id = uid and `month` = mm;
		return 1;
	end if;
END
```