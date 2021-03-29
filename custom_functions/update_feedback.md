``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `update_feedback`(`fid` varchar(255),`iswork` int) RETURNS int
BEGIN
	declare isUpdate int;
	declare uid varchar(255);
	select count(*), user_id into isUpdate, uid from feedback where _id = fid and is_check = 0;
	if isUpdate = 1 then
		update feedback set is_check = 1 where _id = fid;
		if isWork = 0 then
			return 1;
		else
			update `user` set integral_count = integral_count + 5 where _id = uid;
			return 2;
		end if;
	else 
		return 0;
	end if;
END
```