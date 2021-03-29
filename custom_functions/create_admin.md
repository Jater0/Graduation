``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `create_admin`(`aid` varchar(255),`aname` varchar(255),`aemail` varchar(255),`apwd` varchar(255),`alevel` int) RETURNS int
BEGIN
	declare isExist int;
	declare roleD varchar(255);
	select count(*) into isExist from admin where admin_email = aemail;
	select action_default into roleD from admin_role where role_id = `alevel`;
	if isExist = 0 then
		insert into admin(_id, admin_name, admin_email, `password`, professional, create_time, action) values (aid, aname, aemail, apwd, alevel, NOW(), roleD);
		return 0;
	else
		return 1;
	end if;
END
```