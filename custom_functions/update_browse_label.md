``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `update_browse_label`(`uid` varchar(255), `lab` varchar(255), `mm` int, `bro` int) RETURNS int
BEGIN
	declare isExist int;
	declare _err int default 0;
	declare continue handler for SQLEXCEPTION, SQLWARNING, NOT FOUND set _err = 1;
	select count(*) into isExist from browse_label where user_id = uid and label = lab and `month` = mm;
	if _err = 1 then
		return -1;
	else 
		if isExist = 0 then
			insert into browse_label(user_id, label, `month`, browse) values (uid, lab, mm, bro);
			return 0;
		else
			update browse_label set browse = browse + bro where user_id = uid and label = lab and `month` = mm;
			return 1;
		end if;
	end if;
END
```