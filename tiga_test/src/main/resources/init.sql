/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50724
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2021-12-21 10:32:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` int(11) NOT NULL COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '用户名',
  `age` int(11) NOT NULL COMMENT '年龄',
  `remark` varchar(255) NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('1', 'Spike', '8', '汪汪');
INSERT INTO `person` VALUES ('2', 'tom', '6', '汪汪');
INSERT INTO `person` VALUES ('3', 'jerry', '3', '汪汪队');
INSERT INTO `person` VALUES ('4', 'Butch', '5', '汪汪队');

-- ----------------------------
-- Table structure for star
-- ----------------------------
DROP TABLE IF EXISTS `star`;
CREATE TABLE `star` (
    `id` int(10) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `created` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of star
-- ----------------------------
INSERT INTO `star` VALUES ('1', '62C', '2022-10-19 14:48:58');
INSERT INTO `star` VALUES ('3', '62e', '2022-10-21 01:09:54');

