``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `create_label`(`lid` varchar(255),`lname` varchar(255)) RETURNS int
BEGIN
	declare flag int default 0;
	declare uu varchar(255);
	declare _err int default 0;
	declare userList cursor for (select _id from user);
	declare continue handler for not found set flag = 1;
	declare continue handler for SQLEXCEPTION, SQLWARNING set _err = 1;
	if _err = 0 then
		insert into label(_id, label_name, create_time) values (lid, lname, NOW());
		open userList;
			fetch userList into uu;
			while flag != 1 do
				if uu != "admins" then
					insert into user_label(user_id, label_name) values (uu, lname);
					fetch userList into uu;
				else
					fetch userList into uu;
				end if;
			end while;
			close userList;
		RETURN 1;
	else
		return 0;
	end if;
END
```