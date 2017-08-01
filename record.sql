/*
Navicat MySQL Data Transfer

Source Server         : 103.231.66.24
Source Server Version : 50713
Source Host           : 103.231.66.24:3306
Source Database       : game_center

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-08-01 16:17:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for aapt_package_log
-- ----------------------------
DROP TABLE IF EXISTS `aapt_package_log`;
CREATE TABLE `aapt_package_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `applicationId` varchar(125) COLLATE utf8_bin DEFAULT NULL COMMENT '应用包名',
  `themeName` varchar(125) COLLATE utf8_bin DEFAULT NULL COMMENT '主题名',
  `themeDesc` varchar(125) COLLATE utf8_bin DEFAULT NULL COMMENT '主题描述',
  `themeChannel` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '渠道',
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'apk地址',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '打包时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
