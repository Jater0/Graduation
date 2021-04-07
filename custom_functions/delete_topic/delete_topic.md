``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `delete_topic`(`fid` varchar(255)) RETURNS int
BEGIN
	declare is_exist int;
	select count(*) into is_exist from article where father_id = fid;
	update topic_and_article set is_delete = 1 where _id = fid;
	if is_exist = 0 then
		return 0;
	else 
		update article set is_delete = 1 where father_id = fid;
		return 1;
	end if;
END
```