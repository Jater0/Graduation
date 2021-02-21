/*
 Navicat Premium Data Transfer

 Source Server         : mysql_8.0_jater
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : objective_database_test

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 22/02/2021 01:38:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `admin_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `explain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `professional` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'if article\'s == \'article\', then _id == topic_id',
  `topic_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'if article\'s == \'article\', then _id == topic_id',
  `mode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'the type of article, two values topic and article',
  `author_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `author_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `classify` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'OSS URL',
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `browse_count` int(0) NULL DEFAULT NULL,
  `collection_count` int(0) NULL DEFAULT NULL,
  `comments_count` int(0) NULL DEFAULT NULL,
  `thumbs_up_count` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comment_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `is_reply` int(0) NULL DEFAULT 0,
  `father_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tos` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `to_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cover
-- ----------------------------
DROP TABLE IF EXISTS `cover`;
CREATE TABLE `cover`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `topic_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for f_cover
-- ----------------------------
DROP TABLE IF EXISTS `f_cover`;
CREATE TABLE `f_cover`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `feedback_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `feedback_cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `feedback_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_check` int(0) NULL DEFAULT 0,
  `feedback_type` int(0) NULL DEFAULT 1,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label`  (
  `_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `label_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`  (
  `_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `author_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `author_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `classify` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `browse_count` int(0) NULL DEFAULT 0,
  `collection_count` int(0) NULL DEFAULT 0,
  `comments_count` int(0) NULL DEFAULT 0,
  `thumbs_up_count` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for topic_article
-- ----------------------------
DROP TABLE IF EXISTS `topic_article`;
CREATE TABLE `topic_article`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `topic_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `article_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'insert here when article\'s type equals \'topic\'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `author_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `author_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `author_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gender` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `explain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `professional` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fans_count` int(0) NULL DEFAULT NULL,
  `follow_count` int(0) NULL DEFAULT NULL,
  `integral_count` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_article_likes
-- ----------------------------
DROP TABLE IF EXISTS `user_article_likes`;
CREATE TABLE `user_article_likes`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `article_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_like` int(0) NULL DEFAULT 0,
  `is_thumbs_up` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_article_personal
-- ----------------------------
DROP TABLE IF EXISTS `user_article_personal`;
CREATE TABLE `user_article_personal`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `article_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_author_likes
-- ----------------------------
DROP TABLE IF EXISTS `user_author_likes`;
CREATE TABLE `user_author_likes`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_like` int(0) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_label
-- ----------------------------
DROP TABLE IF EXISTS `user_label`;
CREATE TABLE `user_label`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `label_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `current` int(0) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Function structure for create_admin
-- ----------------------------
DROP FUNCTION IF EXISTS `create_admin`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `create_admin`(`aid` varchar(255),`aname` varchar(255),`aemail` varchar(255),`apwd` varchar(255),`alevel` int) RETURNS int
BEGIN
	declare isExist int;
	select count(*) into isExist from admin where admin_email = aemail;
	if isExist = 0 then
		insert into admin(_id, admin_name, admin_email, `password`, professional, create_time) values (aid, aname, aemail, apwd, alevel, NOW());
		return 0;
	else
		return 1;
	end if;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for create_topic
-- ----------------------------
DROP FUNCTION IF EXISTS `create_topic`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `create_topic`(`tid` varchar(255),`aid` varchar(255),`header` varchar(255),`type` varchar(255),`ctime` timestamp) RETURNS int
begin
	declare aname varchar(255);
	declare aavatar varchar(255);
	declare _err int default 0;
	declare continue handler for SQLEXCEPTION, SQLWARNING, NOT FOUND set _err = 1;
	select author_name, avatar into aname, aavatar from user where _id = aid;
	if _err = 1 then
		return 0;
	else
		insert into topic(_id, author_id, author_name, avatar, mode, title, classify, create_time) values (tid, aid, aname, aavatar, "topic", header, type, ctime);
		return 1;
	end if;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for create_user
-- ----------------------------
DROP PROCEDURE IF EXISTS `create_user`;
delimiter ;;
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
;;
delimiter ;

-- ----------------------------
-- Function structure for update_admin
-- ----------------------------
DROP FUNCTION IF EXISTS `update_admin`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `update_admin`(aid varchar(255), aemail varchar(255), aphone varchar(255), aname varchar(255)) RETURNS int
begin
	declare isExist int;
	select count(*) into isExist from admin where (admin_email = aemail or admin_phone = aphone) and _id != aid;
	if isExist = 0 then
		update admin set admin_email = aemail, admin_name = aname, admin_phone = aphone where _id = aid;
		return 0;
	else
		return 1;
	end if;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for update_article_likes
-- ----------------------------
DROP PROCEDURE IF EXISTS `update_article_likes`;
delimiter ;;
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
;;
delimiter ;

-- ----------------------------
-- Function structure for update_article_thumbs
-- ----------------------------
DROP FUNCTION IF EXISTS `update_article_thumbs`;
delimiter ;;
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
			update topic t, article a set t.thumbs_up_count = t.thumbs_up_count + 1, a.thumbs_up_count = a.thumbs_up_count + 1 where a.topic_id =t._id and a._id = aid;
			return 0;
		else
			return 1;
		end if;
	end if;
end
;;
delimiter ;

-- ----------------------------
-- Procedure structure for update_author_likes
-- ----------------------------
DROP PROCEDURE IF EXISTS `update_author_likes`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_author_likes`(uid varchar(255), aid varchar(255))
begin
	declare isExist int;
	select count(*) into isExist from user_author_likes where user_id = uid and author_id = aid;
	if isExist = 0 then
		insert into user_author_likes(user_id, author_id, is_like) values (uid, aid, 1);
	else
		update user_author_likes set is_like = case when(is_like = 0) then 1 else 0 end where user_id = uid and author_id = aid;
	end if;
end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
