``` mysql
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_author_likes`(uid varchar(255), aid varchar(255))
begin
	declare isExist int;
	declare `s_like` int;
	select count(*) into isExist from user_author_likes where user_id = uid and author_id = aid;
	if isExist = 0 then
		insert into user_author_likes(user_id, author_id, is_like) values (uid, aid, 1);
		update user set follow_count = follow_count + 1 where _id = uid;
		update user set fans_count = fans_count + 1 where _id = aid; 
	else
		update user_author_likes set is_like = case when(is_like = 0) then 1 else 0 end where user_id = uid and author_id = aid;
		select is_like into s_like from user_author_likes where user_id = uid and author_id = aid;
		if s_like = 1 then
			update user set follow_count = follow_count + 1 where _id = uid;
			update user set fans_count = fans_count + 1 where _id = aid; 
		else
			update user set follow_count = follow_count - 1 where _id = uid;
			update user set fans_count = fans_count - 1 where _id = aid; 
		end if;
	end if;
end
```