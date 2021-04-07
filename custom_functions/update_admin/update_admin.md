``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `update_admin`(
    aid varchar(255), aemail varchar(255), 
    aphone varchar(255), aname varchar(255)) RETURNS int
begin
	declare isExist int;
	select count(*) into isExist from admin 
		where (admin_email = aemail or admin_phone = aphone) 
		and _id != aid;
	if isExist = 0 then
		update admin set admin_email = aemail, 
			admin_name = aname, 
			admin_phone = aphone where _id = aid;
		return 0;
	else
		return 1;
	end if;
end
```