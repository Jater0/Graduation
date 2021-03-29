``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `delete_topic`(`tid` varchar(255)) RETURNS int
BEGIN
	declare is_exist int;
	select count(*) into is_exist from article where topic_id = tid;
	update topic set is_delete = 1 where _id = tid;
	if is_exist = 0 then
		return 0;
	else 
		update article set is_delete = 1 where topic_id = tid;
		return 1;
	end if;
END
```