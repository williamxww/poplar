DROP DATABASE IF EXISTS `poplar`;
CREATE DATABASE `poplar`
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;
;
USE `poplar`;


CREATE TABLE `app` (
  `appId` varchar(100) COMMENT '应用英文名',
  `appName` varchar(100) COMMENT '应用名称',
  `comment` varchar(100) COMMENT '备注',
  `createTime` varchar(100) COMMENT '创建时间',
  `updateTime` varchar(100) COMMENT '更新时间',
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用';


CREATE TABLE `namespace` (
  `appId` int COMMENT '应用ID',
  `namespace` varchar(100) COMMENT '文件名称',
  `format` varchar(100) COMMENT '格式',
  `comment` varchar(100) COMMENT '备注',
  `createTime` varchar(100) COMMENT '创建时间',
  `updateTime` varchar(100) COMMENT '更新时间',
  PRIMARY KEY (`appId`, `namespace`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='命名空间';


CREATE TABLE `item` (
  `appId` int COMMENT '应用ID',
  `namespace` int COMMENT '命名空间',
  `key` varchar(100) COMMENT '键',
  `value` varchar(100) COMMENT '值',
  `comment` varchar(100) COMMENT '备注',
  `createTime` varchar(100) COMMENT '创建时间',
  `updateTime` varchar(100) COMMENT '更新时间',
  PRIMARY KEY (`appId`, `namespace`, `key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置项';