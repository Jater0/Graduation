``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `update_article_thumbs`(uid varchar(255), aid varchar(255)) RETURNS int
begin
	declare isExist int;
	declare isThumbs int;
	select count(*) into isExist from user_article_likes where user_id = uid and article_id = aid;
	if isExist = 0 then
		insert into user_article_likes(user_id, article_id, is_thumbs_up) values (uid, aid, 1);
		update topic t, article a set t.thumbs_up_count = t.thumbs_up_count + 1, a.thumbs_up_count = a.thumbs_up_count + 1 where a.topic_id =t._id and a._id = aid;
		return 0;
	else
		select is_thumbs_up into isThumbs from user_article_likes where user_id = uid and article_id = aid;
		if isThumbs = 0 then
			update user_article_likes set is_thumbs_up = 1 where user_id = uid and article_id = aid;
			update topic t, article a, user u
				set t.thumbs_up_count = t.thumbs_up_count + 1, 
						a.thumbs_up_count = a.thumbs_up_count + 1,
						u.integral_count =  u.integral_count + 1
						where a.topic_id =t._id and a._id = aid and u._id = a.author_id;
			return 0;
		else
			return 1;
		end if;
	end if;
end
```