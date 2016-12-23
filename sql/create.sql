CREATE DATABASE  IF NOT EXISTS `eop` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `eop`;
-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
-- Server version	5.5.47-MariaDB-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auth_code`
--

DROP TABLE IF EXISTS `auth_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_code` (
  `auth_code_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(30) NOT NULL COMMENT '查询标识',
  `REQUST_TIME` datetime NOT NULL COMMENT '请求时间',
  `AUTH_CODE` varchar(150) NOT NULL COMMENT '验证码',
  `IS_INVALID` char(1) NOT NULL COMMENT '是否有效',
  `TYPE` varchar(20) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`auth_code_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=6407 DEFAULT CHARSET=utf8 COMMENT='验证码表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auth_code_log`
--

DROP TABLE IF EXISTS `auth_code_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_code_log` (
  `auth_code_log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `auth_code_time` datetime DEFAULT NULL COMMENT '发送时间',
  `auth_code_mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `auth_code_type` varchar(20) DEFAULT NULL COMMENT '短信类型',
  `auth_code` varchar(50) DEFAULT NULL COMMENT '验证码',
  `auth_code_ipaddr` varchar(50) DEFAULT NULL COMMENT '登录IP',
  PRIMARY KEY (`auth_code_log_id`)
) ENGINE=MyISAM AUTO_INCREMENT=17830 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uuid` char(36) DEFAULT NULL COMMENT 'uuid',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '昵称',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `status` varchar(2) DEFAULT '0' COMMENT '状态(0删除1正常2锁定)',
  `email` varchar(30) DEFAULT NULL COMMENT '用户的email',
  `mobile` char(11) DEFAULT NULL COMMENT '手机',
  `head_img` varchar(256) DEFAULT NULL,
  `logincount` int(11) DEFAULT '0' COMMENT '登陆次数',
  `lasttime` datetime DEFAULT NULL COMMENT '上次登陆时间',
  `lastip` varchar(15) DEFAULT NULL COMMENT '上次登陆ip',
  `reg_ip` varchar(15) DEFAULT NULL COMMENT '注册ip',
  `reg_time` datetime DEFAULT NULL COMMENT '注册时间',
  `real_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ',
  `id_number` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `user_type` datetime DEFAULT NULL COMMENT '会员类型',
  `province` varchar(10) DEFAULT NULL COMMENT '籍贯',
  `company` varchar(50) DEFAULT NULL COMMENT '单位',
  `company_title` varchar(50) DEFAULT NULL COMMENT '职务',
  `company_addr` varchar(100) DEFAULT NULL COMMENT '单位地址',
  `company_phone` varchar(20) DEFAULT NULL COMMENT '办公电话',
  `school` varchar(30) DEFAULT NULL COMMENT '毕业学校',
  `degree` varchar(10) DEFAULT NULL COMMENT '学历',
  `finish_school` datetime DEFAULT NULL COMMENT '毕业时间',
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;


