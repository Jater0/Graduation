``` mysql
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_article_likes`
	(uid varchar(255), aid varchar(255))
begin 
	declare isExist int;
	declare `s_like` int;
	select count(*) into isExist from user_article_likes 
		where user_id = uid and article_id = aid;
	if isExist = 0 then
		insert into user_article_likes
			(user_id, article_id, is_like) 
			values (uid, aid, 1);
		update user u, article a set 
			u.integral_count = u.integral_count + 2 
			where a.author_id = u._id and a._id = aid;
	else
		update user_article_likes 
			set is_like = case when(is_like=0) then 1 else 0 end 
			where user_id = uid and article_id = aid;
		select is_like into s_like 
			from user_article_likes 
			where user_id = uid and article_id = aid;
		if s_like = 1 then 
			update user u, article a 
				set u.integral_count = u.integral_count + 2 
				where a.author_id = u._id and a._id = aid;
		else 
			update user u, article a 
				set u.integral_count = u.integral_count - 2 
				where a.author_id = u._id and a._id = aid;
		end if;
	end if;
end
```