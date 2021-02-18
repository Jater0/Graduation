# MySQL-DB my procedure and my function
## create_user

```mysql
create procedure create_user(uid varchar(255), uname varchar(20), ugender varchar(5), uavatar varchar(255))
begin 
	declare flag int default 0;
	-- 用来存储label_name数组当前遍历的值
	declare ln varchar(255);
	-- 用来存储 select出来的label_name 数组
	declare labelNames cursor for (select label_name from label);
	-- 当labelNames 遍历完 设置flag = 0
	declare continue handler for not found set flag = 1;
	-- 使用labelNames
	open labelNames;
		-- 创建新的user表数据
		insert into user(_id,author_name,gender,avatar) values (uid,uname,ugender,uavatar);
		FETCH labelNames into ln;
		while flag != 1 do
			-- 循环创建user对于的user_label数据
			insert into user_label(user_id, label_name) values (uid, ln);
			-- 移动下标, 将新的label_name设置给ln变量
			FETCH labelNames into ln;
		end while;
	close labelNames;
end;
```

```mysql
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_user`(uid varchar(255), uname varchar(20), ugender varchar(5), uavatar varchar(255))
begin declare flag int default 0;
	declare ln varchar(255);
	declare labelNames cursor for (select label_name from label);
	declare continue handler for not found set flag = 1;
	open labelNames;
		insert into user(_id,author_name,gender,avatar) values (uid,uname,ugender,uavatar);
		FETCH labelNames into ln;
		while flag != 1 do
			insert into user_label(user_id, label_name) values (uid, ln);
			FETCH labelNames into ln;
		end while;
	close labelNames;
end
```



## update_article_likes

``` mysql
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_article_likes`(uid varchar(255), aid varchar(255))
begin 
	declare isExist int;
	select count(*) into isExist from user_article_likes where user_id = uid and article_id = aid;
	if isExist = 0 then
		insert into user_article_likes(user_id, article_id, is_like) values (uid, aid, 1);
	else
		update user_article_likes set is_like = case when(is_like=0) then 1 else 0 end where user_id = uid and article_id = aid;
	end if;
end
```



## update_author_likes

``` mysql
create procedure update_author_likes(uid varchar(255), aid varchar(255))
begin
	declare isExist int;
	select count(*) into isExist from user_author_likes where user_id = uid and author_id = aid;
	if isExist = 0 then
		insert into user_author_likes(user_id, author_id, is_like) values (uid, aid, 1);
	else
		update user_author_likes set is_like = case when(is_like = 0) then 1 else 0 end where user_id = uid and author_id = aid;
	end if;
end;
```



## update_article_thumbs

``` mysql
CREATE DEFINER=`root`@`localhost` FUNCTION `update_article_thumbs`(uid varchar(255), aid varchar(255)) RETURNS int
begin
	declare isExist int;
	declare isThumbs int;
	select count(*) into isExist from user_article_likes where user_id = uid and article_id = aid;
	if isExist = 0 then
		insert into user_article_likes(user_id, article_id, is_thumbs_up) values (uid, aid, 1);
		return 0;
	else
		select is_thumbs_up into isThumbs from user_article_likes where user_id = uid and article_id = aid;
		if isThumbs = 0 then
			update user_article_likes set is_thumbs_up = 1 where user_id = uid and article_id = aid;
			return 0;
		else
			return 1;
		end if;
	end if;
end
```



