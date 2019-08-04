# noinspection SqlNoDataSourceInspectionForFile


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `coin`
-- ----------------------------
DROP TABLE IF EXISTS `coin`;
CREATE TABLE `coin` (
  `name` varchar(255) NOT NULL,
  `can_auto_withdraw` int(11) DEFAULT NULL,
  `can_recharge` int(11) DEFAULT NULL,
  `can_transfer` int(11) DEFAULT NULL,
  `can_withdraw` int(11) DEFAULT NULL,
  `cny_rate` double NOT NULL,
  `enable_rpc` int(11) DEFAULT NULL,
  `is_platform_coin` int(11) DEFAULT NULL,
  `max_tx_fee` double NOT NULL,
  `max_withdraw_amount` decimal(18,8) DEFAULT NULL COMMENT '最大提币数量',
  `min_tx_fee` double NOT NULL,
  `min_withdraw_amount` decimal(18,8) DEFAULT NULL COMMENT '最小提币数量',
  `name_cn` varchar(255) NOT NULL,
  `sort` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `unit` varchar(255) NOT NULL,
  `usd_rate` double NOT NULL,
  `withdraw_threshold` decimal(18,8) DEFAULT NULL COMMENT '提现阈值',
  `has_legal` bit(1) NOT NULL DEFAULT b'0',
  `cold_wallet_address` varchar(255) DEFAULT NULL,
  `miner_fee` decimal(18,8) DEFAULT '0.00000000' COMMENT '矿工费',
  `withdraw_scale` int(11) DEFAULT '4' COMMENT '提币精度',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coin
-- ----------------------------
INSERT INTO `coin` VALUES ('Bitcoin', '0', '0', '1', '0', '0', '0', '0', '0.0002', '5.00000000', '0.0002', '0.00100000', '比特币', '1', '0', 'BTC', '0', '0.10000000', '', null, '0.00000000', '4');
INSERT INTO `coin` VALUES ('Bitcoincash', '1', '1', '1', '1', '0', '1', '0', '0.0002', '5.00000000', '0.0002', '0.00100000', '比特现金', '1', '0', 'BCH', '0', '0.10000000', '', null, '0.00000000', '4');
INSERT INTO `coin` VALUES ('DASH', '1', '1', '1', '1', '0', '1', '0', '0.0002', '5.00000000', '0.0002', '0.00100000', '达世币', '1', '0', 'DASH', '0', '0.10000000', '', null, '0.00000000', '4');
INSERT INTO `coin` VALUES ('DEV', '1', '0', '1', '0', '1', '0', '0', '0.0002', '5.00000000', '0.0002', '0.00100000', 'DEV', '1', '0', 'DEV', '0', '0.10000000', '', null, '0.00000000', '4');
INSERT INTO `coin` VALUES ('Ethereum', '1', '1', '1', '1', '0', '1', '0', '0.0002', '5.00000000', '0.0002', '0.00100000', '以太坊', '1', '0', 'ETH', '0', '0.10000000', '', null, '0.00000000', '4');
INSERT INTO `coin` VALUES ('GalaxyChain', '1', '1', '1', '1', '1', '1', '0', '0.0002', '5.00000000', '0.0002', '0.00100000', '银河链', '1', '0', 'GCC', '0', '0.10000000', '', null, '0.00000000', '4');
INSERT INTO `coin` VALUES ('Litecoin', '1', '0', '1', '1', '1', '1', '0', '0.0002', '5.00000000', '0.0002', '0.00100000', '莱特币', '1', '0', 'LTC', '0', '0.10000000', '', null, '0.00000000', '4');
INSERT INTO `coin` VALUES ('RMB', '1', '1', '1', '1', '0', '0', '0', '0.0002', '5.00000000', '0.0002', '0.00100000', '零币TEST', '1', '1', 'ZEC', '0', '0.10000000', '', null, '0.00000000', '4');
INSERT INTO `coin` VALUES ('SGD', '1', '1', '1', '1', '0', '1', '0', '0.0002', '500.00000000', '1', '1.00000000', '新币', '4', '0', 'SGD', '0', '0.10000000', '', null, '0.00000000', '4');
INSERT INTO `coin` VALUES ('USDT', '1', '1', '1', '1', '0', '1', '0', '0.0002', '5.00000000', '0.0002', '0.00100000', '泰达币T', '1', '0', 'USDT', '0', '0.10000000', '', null, '0.00000000', '4');
INSERT INTO `coin` VALUES ('ZEC', '1', '0', '1', '1', '0', '0', '1', '0.0002', '5.00000000', '0.0002', '0.00100000', '零币KT1', '1', '0', 'ZEC', '0', '0.10000000', '', null, null, '4');

-- ----------------------------
-- Table structure for `exchange_coin`
-- ----------------------------
DROP TABLE IF EXISTS `exchange_coin`;
CREATE TABLE `exchange_coin` (
  `symbol` varchar(255) NOT NULL,
  `base_coin_scale` int(11) NOT NULL,
  `base_symbol` varchar(255) DEFAULT NULL,
  `coin_scale` int(11) NOT NULL,
  `coin_symbol` varchar(255) DEFAULT NULL,
  `enable` int(11) NOT NULL,
  `fee` decimal(8,4) DEFAULT NULL COMMENT '交易手续费',
  `sort` int(11) NOT NULL,
  `enable_market_buy` int(11) DEFAULT '1' COMMENT '是否启用市价买',
  `enable_market_sell` int(11) DEFAULT '1' COMMENT '是否启用市价卖',
  `min_sell_price` decimal(18,8) DEFAULT '0.00000000' COMMENT '最低挂单卖价',
  `flag` int(11) DEFAULT '0',
  `max_trading_order` int(11) DEFAULT '0' COMMENT '最大允许同时交易的订单数，0表示不限制',
  `max_trading_time` int(11) DEFAULT '0' COMMENT '委托超时自动下架时间，单位为秒，0表示不过期',
  `instrument` varchar(20) DEFAULT NULL COMMENT '交易类型，B2C2特有',
  `min_turnover` decimal(18,8) DEFAULT '0.00000000' COMMENT '最小挂单成交额',
  PRIMARY KEY (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exchange_coin
-- ----------------------------
INSERT INTO `exchange_coin` VALUES ('BCH/USDT', '2', 'USDT', '4', 'BCH', '1', '0.0002', '2', '1', '1', '0.00000000', '1', '0', '0', null, '0.00000000');
INSERT INTO `exchange_coin` VALUES ('BTC/USDT', '2', 'USDT', '2', 'BTC', '1', '0.0010', '1', '1', '1', '0.00000000', '1', '0', '0', null, '0.00000000');
INSERT INTO `exchange_coin` VALUES ('DASH/USDT', '2', 'USDT', '4', 'DASH', '2', '0.0010', '6', '1', '1', '0.00000000', '1', '0', '0', null, '0.00000000');
INSERT INTO `exchange_coin` VALUES ('ETH/USDT', '2', 'USDT', '2', 'ETH', '1', '0.0010', '3', '1', '1', '0.00000000', '0', '0', '0', null, '0.00000000');
INSERT INTO `exchange_coin` VALUES ('LTC/USDT', '2', 'USDT', '4', 'LTC', '1', '0.0010', '5', '1', '1', '0.00000000', '0', '0', '0', null, '0.00000000');
INSERT INTO `exchange_coin` VALUES ('ZEC/USDT', '2', 'USDT', '4', 'ZEC', '1', '0.1200', '7', '1', '1', '0.00000000', '0', '0', '0', null, '0.00000000');

-- ----------------------------
-- Table structure for `otc_coin`
-- ----------------------------
DROP TABLE IF EXISTS `otc_coin`;
CREATE TABLE `otc_coin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buy_min_amount` decimal(18,8) DEFAULT NULL COMMENT '买入广告最低发布数量',
  `is_platform_coin` int(11) DEFAULT NULL,
  `jy_rate` decimal(18,6) DEFAULT NULL COMMENT '交易手续费率',
  `name` varchar(255) NOT NULL,
  `name_cn` varchar(255) NOT NULL,
  `sell_min_amount` decimal(18,8) DEFAULT NULL COMMENT '卖出广告最低发布数量',
  `sort` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `unit` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of otc_coin
-- ----------------------------
INSERT INTO `otc_coin` VALUES ('1', '0.01000000', '0', '1.000000', 'Bitcoin', '比特币', '0.10000000', '1', '0', 'BTC');
INSERT INTO `otc_coin` VALUES ('2', '0.01000000', '0', '1.000000', 'USDT', '泰达币', '0.10000000', '3', '0', 'USDT');
INSERT INTO `otc_coin` VALUES ('3', '0.01000000', '0', '1.000000', 'Ethereum', '以太坊', '0.10000000', '2', '0', 'ETH');
INSERT INTO `otc_coin` VALUES ('4', '8.00000000', '0', '9.000000', 'Bitcoin Cash', '比特币现金', '8.00000000', '0', '0', 'BCH');
INSERT INTO `otc_coin` VALUES ('5', '1.00000000', '0', '1.000000', 'Litecoin', '莱特币', '1.00000000', '0', '1', 'LTC');
INSERT INTO `otc_coin` VALUES ('6', '1.00000000', '0', '1.000000', 'SGD', '新币', '1.00000000', '0', '1', 'SGD');



SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `country`
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `zh_name` varchar(255) NOT NULL,
  `area_code` varchar(255) DEFAULT NULL,
  `en_name` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `local_currency` varchar(255) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  PRIMARY KEY (`zh_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of country
-- ----------------------------
INSERT INTO `country` VALUES ('中国', '86', 'China', 'zh_CN', 'CNY', '0');
INSERT INTO `country` VALUES ('中国台湾', '886', 'Taiwan,China', 'zh_TW', 'TWD', '0');
INSERT INTO `country` VALUES ('新加坡', '65', 'Singapore', 'en_US', 'SGD', '0');
INSERT INTO `country` VALUES ('美国', '1', 'America', 'en_US', 'USD', '0');
INSERT INTO `country` VALUES ('香港', '852', 'HongKong', 'zh_HK', 'HKD', '0');


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `last_login_ip` varchar(255) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `mobile_phone` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `department_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gfn44sntic2k93auag97juyij` (`username`) USING BTREE,
  KEY `FKibjnyhe6m46qfkc6vgbir1ucq` (`department_id`) USING BTREE,
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  CONSTRAINT `admin_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  CONSTRAINT `admin_ibfk_3` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  CONSTRAINT `FKnmmt6f2kg0oaxr11uhy7qqf3w` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'kjglkasdjg/sdfasdf/asdf', 'root', '0', '0:0:0:0:0:0:0:1', '2018-05-24 15:06:00', '18225520751', '47943eeeab5ed28f8a93f7fb77ec5111', '312', '人人', '1', '0', 'root', '1');


-- ----------------------------
-- Table structure for `admin_permission`
-- ----------------------------
DROP TABLE IF EXISTS `admin_permission`;
CREATE TABLE `admin_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_permission
-- ----------------------------
INSERT INTO `admin_permission` VALUES ('2', '角色管理', 'system:role:all', '8', '0', '角色管理');
INSERT INTO `admin_permission` VALUES ('3', '用户管理', 'system:employee:page-query', '8', '0', '用户管理');
INSERT INTO `admin_permission` VALUES ('4', '部门管理', 'system:department:all', '8', '0', '部门管理');
INSERT INTO `admin_permission` VALUES ('5', '站点管理', 'system:website-information:find-one', '8', '0', '站点管理');
INSERT INTO `admin_permission` VALUES ('6', '菜单管理', 'system:role:permission:all', '8', '0', '菜单管理');
INSERT INTO `admin_permission` VALUES ('7', '系统日志', 'system:access-log:page-query', '8', '0', '系统日志');
INSERT INTO `admin_permission` VALUES ('8', '系统管理', 'system-------', '0', '6', '系统管理');
INSERT INTO `admin_permission` VALUES ('9', '广告管理', 'cms:system-advertise:page-query', '18', '0', '广告管理');
INSERT INTO `admin_permission` VALUES ('10', '帮助管理', 'cms:system-help:page-query', '18', '0', '帮助管理');
INSERT INTO `admin_permission` VALUES ('11', '会员管理', 'member--------', '0', '1', '会员管理');
INSERT INTO `admin_permission` VALUES ('12', '会员管理', 'member:page-query', '11', '0', '会员管理');
INSERT INTO `admin_permission` VALUES ('13', '实名管理', 'member:member-application:page-query', '11', '0', '实名管理');
INSERT INTO `admin_permission` VALUES ('14', '会员监控', 'member--------', '11', '0', '会员监控');
INSERT INTO `admin_permission` VALUES ('18', '内容管理', 'cms-------', '0', '4', '内容管理');
INSERT INTO `admin_permission` VALUES ('19', '交易明细', 'finance:member-transaction:page-query', '93', '0', '交易记录');
INSERT INTO `admin_permission` VALUES ('20', '提现管理', 'finance:withdraw-record:page-query', '93', '0', '提现管理');
INSERT INTO `admin_permission` VALUES ('23', '币种管理', 'system:coin:page-query', '8', '0', '币币币种管理');
INSERT INTO `admin_permission` VALUES ('26', '公告管理', 'cms:notice', '18', '0', '公告管理');
INSERT INTO `admin_permission` VALUES ('27', '创建修改角色', 'system:role:merge', '8', '0', '创建修改角色');
INSERT INTO `admin_permission` VALUES ('28', '更改角色权限', 'system:role:permission:update', '8', '0', '更改角色权限');
INSERT INTO `admin_permission` VALUES ('29', '角色拥有权限', 'system:role:permission', '8', '0', '角色拥有权限');
INSERT INTO `admin_permission` VALUES ('30', '全部权限树', 'system:role:permission:all', '8', '0', '全部权限树');
INSERT INTO `admin_permission` VALUES ('31', '创建更改后台用户', 'system:employee:merge', '8', '0', '创建更改后台用户');
INSERT INTO `admin_permission` VALUES ('32', '后台用户详情', 'system:employee:detail', '8', '0', '后台用户详情');
INSERT INTO `admin_permission` VALUES ('33', '站点信息修改', 'system:website-information:alter', '8', '0', '站点信息修改');
INSERT INTO `admin_permission` VALUES ('34', '系统日志详情', 'system:access-log:detail', '8', '0', '系统日志详情');
INSERT INTO `admin_permission` VALUES ('35', '创建系统广告', 'cms:system-advertise:create', '18', '0', '创建系统广告');
INSERT INTO `admin_permission` VALUES ('36', '所有系统广告', 'cms:system-advertise:all', '18', '0', '所有系统广告');
INSERT INTO `admin_permission` VALUES ('37', '系统广告详情', 'cms:system-advertise:detail', '18', '0', '系统广告详情');
INSERT INTO `admin_permission` VALUES ('38', '更新系统广告', 'cms:system-advertise:update', '18', '0', '更新系统广告');
INSERT INTO `admin_permission` VALUES ('39', '删除系统广告', 'cms:system-advertise:deletes', '18', '0', '删除系统广告');
INSERT INTO `admin_permission` VALUES ('40', '导出广告excel', 'cms:system-advertise:out-excel', '18', '0', '导出广告excel');
INSERT INTO `admin_permission` VALUES ('41', '创建系统帮助', 'cms:system-help:create', '18', '0', '创建系统帮助');
INSERT INTO `admin_permission` VALUES ('42', '系统帮助详情', 'cms:system-help:detail', '18', '0', '系统帮助详情');
INSERT INTO `admin_permission` VALUES ('43', '更新系统帮助', 'cms:system-help:update', '18', '0', '更新系统帮助');
INSERT INTO `admin_permission` VALUES ('44', '删除系统帮助', 'cms:system-help:deletes', '18', '0', '删除系统帮助');
INSERT INTO `admin_permission` VALUES ('45', '导出系统帮助excel', 'cms:system-help:out-excel', '18', '0', '导出系统帮助excel');
INSERT INTO `admin_permission` VALUES ('46', '会员详情', 'member:detail', '11', '0', '会员详情');
INSERT INTO `admin_permission` VALUES ('47', '会员删除', 'member:delete', '11', '0', '会员删除');
INSERT INTO `admin_permission` VALUES ('48', '会员更新', 'member:update', '11', '0', '会员更新');
INSERT INTO `admin_permission` VALUES ('49', '认证商家审核', 'member:audit-business', '66', '0', '认证商家审核');
INSERT INTO `admin_permission` VALUES ('50', '导出会员excel', 'member:out-excel', '11', '0', '导出会员excel');
INSERT INTO `admin_permission` VALUES ('51', '实名信息详情', 'member:member-application:detail', '11', '0', '实名信息详情');
INSERT INTO `admin_permission` VALUES ('52', '实名审核通过', 'member:member-application:pass', '11', '0', '实名审核通过');
INSERT INTO `admin_permission` VALUES ('53', '实名审核不通过', 'member:member-application:no-pass', '11', '0', '实名审核不通过');
INSERT INTO `admin_permission` VALUES ('54', '交易记录详情', 'finance:member-transaction:detail', '93', '0', '交易记录详情');
INSERT INTO `admin_permission` VALUES ('55', '导出交易记录excel', 'finance:member-transaction:out-excel', '93', '0', '导出交易记录excel');
INSERT INTO `admin_permission` VALUES ('56', '提现记录详情', 'finance:withdraw-record:detail', '93', '0', '提现记录详情');
INSERT INTO `admin_permission` VALUES ('57', '提现记录一键审核通过', 'finance:withdraw-record:audit-pass', '93', '0', '提现记录一键审核通过');
INSERT INTO `admin_permission` VALUES ('58', '提现记录一键审核不通过', 'finance:withdraw-record:audit-no-pass', '93', '0', '提现记录一键审核不通过');
INSERT INTO `admin_permission` VALUES ('59', '批量打款', 'finance:withdraw-record:remittance', '93', '0', '批量打款');
INSERT INTO `admin_permission` VALUES ('60', '币币管理', 'exchange-------', '0', '3', '币币管理父菜单');
INSERT INTO `admin_permission` VALUES ('61', '币币交易订单详情', 'exchange:exchange-order:detail', '60', '0', '币币交易订单详情');
INSERT INTO `admin_permission` VALUES ('62', '订单管理', 'exchange:exchange-order:page-query', '60', '0', '币币交易订单');
INSERT INTO `admin_permission` VALUES ('63', '导出币币交易订单excel', 'exchange:exchange-order:out-excel', '60', '0', '导出币币交易订单excel');
INSERT INTO `admin_permission` VALUES ('64', '币种管理', 'exchange:exchange-coin:page-query', '60', '0', '币种管理');
INSERT INTO `admin_permission` VALUES ('65', '币币交易对详情', 'exchange:exchange-coin:detail', '60', '0', '币币交易对详情');
INSERT INTO `admin_permission` VALUES ('66', '法币管理', 'otc-------', '0', '2', '法币otc');
INSERT INTO `admin_permission` VALUES ('67', '后台广告详情', 'otc:advertise:detail', '66', '0', '后台广告详情');
INSERT INTO `admin_permission` VALUES ('68', '关闭后台广告', 'otc:advertise:turnoff', '66', '0', '关闭后台广告');
INSERT INTO `admin_permission` VALUES ('69', '后台广告状态修改', 'otc:advertise:alter-status', '66', '0', '后台广告状态修改');
INSERT INTO `admin_permission` VALUES ('70', '后台广告', 'otc:advertise:page-query', '66', '0', '后台广告');
INSERT INTO `admin_permission` VALUES ('71', '后台广告导出excel', 'otc:advertise:out-excel', '66', '0', '后台广告导出excel');
INSERT INTO `admin_permission` VALUES ('72', '后台申诉', 'otc:appeal:page-query', '66', '0', '后台申诉');
INSERT INTO `admin_permission` VALUES ('73', '申诉详情', 'otc:appeal:detail', '66', '0', '申诉详情');
INSERT INTO `admin_permission` VALUES ('74', '申诉处理', 'otc:appeal:audit', '66', '0', '申诉处理');
INSERT INTO `admin_permission` VALUES ('75', '法币交易订单详情', 'otc:order:detail', '66', '0', '法币交易订单详情');
INSERT INTO `admin_permission` VALUES ('76', '法币交易订单状态修改', 'otc:order:alert-status', '66', '0', '法币交易订单状态修改');
INSERT INTO `admin_permission` VALUES ('77', '订单管理', 'otc:order:page-query', '66', '0', '法币交易订单');
INSERT INTO `admin_permission` VALUES ('78', '导出法币交易订单excel', 'otc:order:out-excel', '66', '0', '导出法币交易订单excel');
INSERT INTO `admin_permission` VALUES ('79', '创建otc币种', 'otc:otc-coin:create', '66', '0', '创建otc币种');
INSERT INTO `admin_permission` VALUES ('80', 'otc币种详情', 'otc:otc-coin:detail', '66', '0', 'otc币种详情');
INSERT INTO `admin_permission` VALUES ('81', 'otc币种更新', 'otc:otc-coin:update', '66', '0', 'otc币种更新');
INSERT INTO `admin_permission` VALUES ('82', 'otc币种交易率修改', 'otc:otc-coin:alter-jy-rate', '66', '0', 'otc币种交易率修改');
INSERT INTO `admin_permission` VALUES ('83', '币种管理', 'otc:otc-coin:page-query', '66', '0', '法币币种管理');
INSERT INTO `admin_permission` VALUES ('84', '导出otc币种excel', 'otc:otc-coin:out-excel', '66', '0', '导出otc币种excel');
INSERT INTO `admin_permission` VALUES ('85', '创建后台货币', 'system:coin:create', '8', '0', '创建后台货币');
INSERT INTO `admin_permission` VALUES ('86', '部门详情', 'system:department:detail', '8', '0', '部门详情');
INSERT INTO `admin_permission` VALUES ('87', '查询新增用户曲线', 'system:statistics:member-statistics', '8', '0', '查询新增用户曲线');
INSERT INTO `admin_permission` VALUES ('88', '委托量曲线', 'system:statistics:delegation-statistics', '8', '0', '委托量曲线');
INSERT INTO `admin_permission` VALUES ('89', '法币交易订单量曲线', 'system:statistics:order-statistics', '8', '0', '法币交易订单量曲线');
INSERT INTO `admin_permission` VALUES ('90', 'otc_order统计', 'system:statistics:dashboard', '8', '0', 'otc_order统计');
INSERT INTO `admin_permission` VALUES ('91', '余额管理', 'member:member-wallet:balance', '11', '0', '余额管理');
INSERT INTO `admin_permission` VALUES ('92', '充值管理', 'finance:member-transaction:page-query:recharge', '93', '0', '充值管理');
INSERT INTO `admin_permission` VALUES ('93', '财务管理', 'finance-------', '0', '5', '财务管理');
INSERT INTO `admin_permission` VALUES ('94', '提币审核', 'finance:member-transaction:page-query:check', '93', '0', '提现客服审核');
INSERT INTO `admin_permission` VALUES ('95', '手续费管理', 'finance:member-transaction:page-query:fee', '93', '0', '手续费管理');
INSERT INTO `admin_permission` VALUES ('96', '创建公告', 'system:announcement:create', '8', '0', '创建公告');
INSERT INTO `admin_permission` VALUES ('97', '分页查询公告', 'system:announcement:page-query', '8', '0', '分页查询公告');
INSERT INTO `admin_permission` VALUES ('98', '删除公告', 'system:announcement:delete', '8', '0', '删除公告');
INSERT INTO `admin_permission` VALUES ('99', '公告详情', 'system:announcement:detail', '8', '0', '公告详情');
INSERT INTO `admin_permission` VALUES ('100', '更新公告', 'system:announcement:update', '8', '0', '更新公告');
INSERT INTO `admin_permission` VALUES ('101', '关闭公告', 'system:announcement:turn-off', '8', '0', '关闭公告');
INSERT INTO `admin_permission` VALUES ('102', '打开公告', 'system:announcement:turn-on', '8', '0', '打开公告');
INSERT INTO `admin_permission` VALUES ('103', '币币设置', 'exchange:set', '60', '0', '币币设置');
INSERT INTO `admin_permission` VALUES ('104', '放币处理', 'otc:appeal:release-coin', '66', '0', '放币处理');
INSERT INTO `admin_permission` VALUES ('105', '取消订单', 'otc:appeal:cancel-order', '66', '0', '取消订单');
INSERT INTO `admin_permission` VALUES ('106', '创建修改部门', 'system:department:merge', '8', '0', '创建修改部门');
INSERT INTO `admin_permission` VALUES ('107', '删除exchangeCoin', 'exchange:exchange-coin:deletes', '60', '0', '删除exchangeCoin');
INSERT INTO `admin_permission` VALUES ('108', '删除otcCoin', 'otc:otc-coin:deletes', '66', '0', '删除otcCoin');
INSERT INTO `admin_permission` VALUES ('109', '删除部门', 'system:department:deletes', '8', '0', '删除部门');
INSERT INTO `admin_permission` VALUES ('110', '增加/修改权限', 'system:permission:merge', '8', '0', '增加权限');
INSERT INTO `admin_permission` VALUES ('111', '权限管理', 'system:permission:page-query', '8', '0', '分页查询权限');
INSERT INTO `admin_permission` VALUES ('112', '权限详情', 'system:permission:details', '8', '0', '权限详情');
INSERT INTO `admin_permission` VALUES ('113', '权限删除', 'system:permission:deletes', '8', '0', '权限删除');
INSERT INTO `admin_permission` VALUES ('114', '添加交易流水号', 'finance:withdraw-record:add-transaction-number', '93', '0', '财务提现转账成功添加流水号');
INSERT INTO `admin_permission` VALUES ('115', '人工充值', 'member:member-wallet:recharge', '11', '0', '人工充值');
INSERT INTO `admin_permission` VALUES ('116', '首页订单数', 'otc:order:get-order-num', '66', '0', '首页订单数');
INSERT INTO `admin_permission` VALUES ('117', '投票管理', 'system:vote:merge', '8', '0', '新增/修改投票');
INSERT INTO `admin_permission` VALUES ('118', '分页查询', 'system:vote:page-query', '8', '0', '分页查询');
INSERT INTO `admin_permission` VALUES ('119', 'admin更改密码', 'system:employee:update-password', '8', '0', 'admin更改密码');
INSERT INTO `admin_permission` VALUES ('120', '系统公告置顶', 'cms:system-help:top', '18', '0', '系统公告置顶');
INSERT INTO `admin_permission` VALUES ('121', '系统广告置顶', 'cms:system-advertise:top', '18', '0', '系统广告置顶');
INSERT INTO `admin_permission` VALUES ('122', '公告置顶', 'system:announcement:top', '8', '0', '公告置顶');
INSERT INTO `admin_permission` VALUES ('123', '转账地址', 'system:transfer-address:page-query', '8', '0', '转账地址管理    拍币网独有');
INSERT INTO `admin_permission` VALUES ('124', '新增/修改转账地址', 'system:transfer-address:merge', '8', '0', '新增/修改转账地址  拍币网独有');
INSERT INTO `admin_permission` VALUES ('125', '转账地址详情', 'system:transfer-address:detail', '8', '0', '转账地址详情  拍币网独有');
INSERT INTO `admin_permission` VALUES ('126', '批量删除转账地址', 'system:transfer-address:deletes', '8', '0', '批量删除转账地址   拍币网独有');
INSERT INTO `admin_permission` VALUES ('128', '分红管理', 'system:dividend:page-query', '8', '0', '分红管理分页查询');
INSERT INTO `admin_permission` VALUES ('129', '开始分红', 'system:dividend:start', '8', '0', '开始分红');
INSERT INTO `admin_permission` VALUES ('130', '分红手续费', 'system:dividend:fee-query', '8', '0', '分红手续费');
INSERT INTO `admin_permission` VALUES ('131', '充币记录', 'finance:member-deposit:page-query', '93', '0', '区块链钱包充币记录');
INSERT INTO `admin_permission` VALUES ('132', '人工转账', 'system:coin:transfer', '8', '0', '热钱包转账至冷钱包');
INSERT INTO `admin_permission` VALUES ('133', '转入明细', 'system:coin:hot-transfer-record:page-query', '8', '0', '热钱包转入冷钱包记录');
INSERT INTO `admin_permission` VALUES ('134', '实名认证配置修改', 'system:member-application-config:merge', '8', '0', '实名认证配置修改');
INSERT INTO `admin_permission` VALUES ('135', '实名认证配置详情', 'system:member-application-config:detail', '8', '0', '实名认证配置详情');
INSERT INTO `admin_permission` VALUES ('136', '禁用/解禁发布广告', 'member:alter-publish-advertisement-status', '11', '0', '禁用/解禁发布广告 1表示正常');
INSERT INTO `admin_permission` VALUES ('137', '禁用/解禁会员账号', 'member:alter-status', '11', '0', '禁用/解禁会员账号 0表示正常');
INSERT INTO `admin_permission` VALUES ('138', '推荐会员', 'promotion:member:page-query', '143', '0', '推荐会员分页');
INSERT INTO `admin_permission` VALUES ('139', '删除用户', 'system:employee:deletes', '8', '0', '批量删除用户');
INSERT INTO `admin_permission` VALUES ('140', '充值管理', 'legal-wallet-recharge', '66', '0', '充值管理分页');
INSERT INTO `admin_permission` VALUES ('141', '提币管理', 'legal-wallet-withdraw', '66', '0', '提币管理查询分页');
INSERT INTO `admin_permission` VALUES ('142', '是否禁止交易', 'member:alter-transaction-status', '11', '0', '是否禁止交易  1表示正常');
INSERT INTO `admin_permission` VALUES ('143', '推荐返佣', 'promotion-------', '0', '8', '推荐返佣 根');
INSERT INTO `admin_permission` VALUES ('144', '新增/修改推荐返佣配置', 'promotion:reward:merge', '143', '0', '新增/修改推荐返佣配置');
INSERT INTO `admin_permission` VALUES ('145', '重置会员钱包地址', 'member:member-wallet:reset-address', '11', '0', '重置会员钱包地址');
INSERT INTO `admin_permission` VALUES ('146', '佣金参数', 'promotion:reward:page-query', '143', '0', '佣金参数');
INSERT INTO `admin_permission` VALUES ('147', '系统信息维护', 'system:data-dictionary', '8', '0', '系统信息管理');
INSERT INTO `admin_permission` VALUES ('148', '数据字典添加', 'system:data-dictionary:create', '8', '0', '数据字典添加');
INSERT INTO `admin_permission` VALUES ('149', '数据字典修改', 'system:data-dictionary:update', '8', '0', '数据字典修改');
INSERT INTO `admin_permission` VALUES ('150', '版本管理', 'system:app-revision:page-query', '8', '0', '版本管理');
INSERT INTO `admin_permission` VALUES ('151', '添加版本信息', 'system:app-revision:create', '8', '0', '添加版本信息');
INSERT INTO `admin_permission` VALUES ('152', '更新版本信息', 'system:app-revision:update', '8', '0', '更新版本信息');
INSERT INTO `admin_permission` VALUES ('153', '版本信息详情', 'system:app-revision:details', '8', '0', '版本信息详情');
INSERT INTO `admin_permission` VALUES ('154', '推荐会员导出', 'promotion:member:out-excel', '143', '0', '推荐会员导出');
INSERT INTO `admin_permission` VALUES ('155', '推荐会员明细', 'promotion:member:details', '143', '0', '推荐会员明细');
INSERT INTO `admin_permission` VALUES ('156', '测试权限', '测试名称', '18', '0', '描述');
INSERT INTO `admin_permission` VALUES ('158', '取消委托', 'exchange:exchange-order:cancel', '18', '0', '取消委托订单');
INSERT INTO `admin_permission` VALUES ('159', '法币交易明细', 'finance:otc:order:page-query', '93', '0', '法币交易明细');
INSERT INTO `admin_permission` VALUES ('160', '提币明细', 'finance:withdraw-record:page-query:success', '93', '0', '提币明细');
INSERT INTO `admin_permission` VALUES ('161', '保证金管理', 'business-auth:manager', '93', '0', '保证金管理');
INSERT INTO `admin_permission` VALUES ('162', '活动管理', 'activity-------', '0', '7', '活动管理');
INSERT INTO `admin_permission` VALUES ('164', '签到新增', 'activity:sign:post', '162', '0', '签到新增');
INSERT INTO `admin_permission` VALUES ('165', '签到修改', 'activity:sign:put', '162', '0', '签到修改');
INSERT INTO `admin_permission` VALUES ('167', '签到管理', 'activity:sign:page-query', '162', '0', '签到分页');
INSERT INTO `admin_permission` VALUES ('168', '签到详情', 'activity:sign:id:get', '162', '0', '签到详情');
INSERT INTO `admin_permission` VALUES ('169', '签到提前关闭', 'activity:sign:id:early-closing', '162', '0', '签到提前关闭');
INSERT INTO `admin_permission` VALUES ('170', '签到记录', 'activity:member-sign-record:page-query', '162', '0', '签到记录');
INSERT INTO `admin_permission` VALUES ('171', '财务统计', 'finance:statistics:turnover', '93', '0', '成交量/成交额统计');
INSERT INTO `admin_permission` VALUES ('172', '手续费合计', 'finance:statistics:fee', '93', '0', '手续费合计');
INSERT INTO `admin_permission` VALUES ('173', '锁定钱包', 'member:member-wallet:lock-wallet', '11', '0', '锁定钱包');
INSERT INTO `admin_permission` VALUES ('174', '解锁钱包', 'member:member-wallet:unlock-wallet', '11', '0', '解锁钱包');
INSERT INTO `admin_permission` VALUES ('176', '角色删除', 'system:role:deletes', '8', '0', '角色删除');
INSERT INTO `admin_permission` VALUES ('177', '保证金管理', 'business:auth:deposit', '0', '8', '保证金管理');
INSERT INTO `admin_permission` VALUES ('178', '查询保证金策略', 'business:auth:deposit:page', '177', '0', '查询保证金策略');
INSERT INTO `admin_permission` VALUES ('179', '创建保证金策略', 'business:auth:deposit:create', '177', '0', '创建保证金策略');
INSERT INTO `admin_permission` VALUES ('180', '修改保证金策略', 'business:auth:deposit:update', '177', '0', '修改保证金策略');
INSERT INTO `admin_permission` VALUES ('181', '退保审核', 'business:cancel-apply:check', '66', '0', '退保审核');
INSERT INTO `admin_permission` VALUES ('182', '退保管理', 'business:cancel-apply:page-query', '66', '0', '退保申请记录');
INSERT INTO `admin_permission` VALUES ('183', '退保用户详情', 'business:cancel-apply:detail', '66', '0', '退保用户详情');
INSERT INTO `admin_permission` VALUES ('184', '认证商家', 'business-auth:apply:page-query', '66', '0', '认证商家');
INSERT INTO `admin_permission` VALUES ('185', '佣金参数详情', 'promotion:reward:detail', '143', '0', '佣金参数详情');
INSERT INTO `admin_permission` VALUES ('186', '认证商家详情', 'business-auth:apply:detail', '66', '0', '认证商家详情');
INSERT INTO `admin_permission` VALUES ('187', '币种详情', 'system:coin:detail', '8', '0', '系统管理下币种详情');

-- ----------------------------
-- Table structure for `admin_role`
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('1', '系统管理员', '系统管理员');
INSERT INTO `admin_role` VALUES ('61', '客服', '客服');
INSERT INTO `admin_role` VALUES ('62', '技术', '技术');
INSERT INTO `admin_role` VALUES ('84', '测试', '测试');
INSERT INTO `admin_role` VALUES ('85', '', 'Cayman');
INSERT INTO `admin_role` VALUES ('86', 'aaa22', 'aaa');

-- ----------------------------
-- Table structure for `admin_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_permission`;
CREATE TABLE `admin_role_permission` (
  `role_id` bigint(20) NOT NULL,
  `rule_id` bigint(20) NOT NULL,
  UNIQUE KEY `UKplesprlvm1sob8nl9yc5rgh3m` (`role_id`,`rule_id`),
  KEY `FK52rddd3qje4p49iubt08gplb5` (`role_id`) USING BTREE,
  KEY `FKqf3fhgl5mjqqb0jeupx7yafh0` (`rule_id`) USING BTREE,
  CONSTRAINT `admin_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `admin_role` (`id`),
  CONSTRAINT `admin_role_permission_ibfk_2` FOREIGN KEY (`rule_id`) REFERENCES `admin_permission` (`id`),
  CONSTRAINT `admin_role_permission_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `admin_role` (`id`),
  CONSTRAINT `admin_role_permission_ibfk_4` FOREIGN KEY (`rule_id`) REFERENCES `admin_permission` (`id`),
  CONSTRAINT `FK52rddd3qje4p49iubt08gplb5` FOREIGN KEY (`role_id`) REFERENCES `admin_role` (`id`),
  CONSTRAINT `FKqf3fhgl5mjqqb0jeupx7yafh0` FOREIGN KEY (`rule_id`) REFERENCES `admin_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_role_permission
-- ----------------------------
INSERT INTO `admin_role_permission` VALUES ('1', '2');
INSERT INTO `admin_role_permission` VALUES ('1', '3');
INSERT INTO `admin_role_permission` VALUES ('1', '4');
INSERT INTO `admin_role_permission` VALUES ('1', '5');
INSERT INTO `admin_role_permission` VALUES ('1', '6');
INSERT INTO `admin_role_permission` VALUES ('1', '7');
INSERT INTO `admin_role_permission` VALUES ('1', '8');
INSERT INTO `admin_role_permission` VALUES ('1', '9');
INSERT INTO `admin_role_permission` VALUES ('1', '10');
INSERT INTO `admin_role_permission` VALUES ('1', '11');
INSERT INTO `admin_role_permission` VALUES ('1', '12');
INSERT INTO `admin_role_permission` VALUES ('1', '13');
INSERT INTO `admin_role_permission` VALUES ('1', '14');
INSERT INTO `admin_role_permission` VALUES ('1', '18');
INSERT INTO `admin_role_permission` VALUES ('1', '19');
INSERT INTO `admin_role_permission` VALUES ('1', '20');
INSERT INTO `admin_role_permission` VALUES ('1', '23');
INSERT INTO `admin_role_permission` VALUES ('1', '26');
INSERT INTO `admin_role_permission` VALUES ('1', '27');
INSERT INTO `admin_role_permission` VALUES ('1', '28');
INSERT INTO `admin_role_permission` VALUES ('1', '29');
INSERT INTO `admin_role_permission` VALUES ('1', '30');
INSERT INTO `admin_role_permission` VALUES ('1', '31');
INSERT INTO `admin_role_permission` VALUES ('1', '32');
INSERT INTO `admin_role_permission` VALUES ('1', '33');
INSERT INTO `admin_role_permission` VALUES ('1', '34');
INSERT INTO `admin_role_permission` VALUES ('1', '35');
INSERT INTO `admin_role_permission` VALUES ('1', '36');
INSERT INTO `admin_role_permission` VALUES ('1', '37');
INSERT INTO `admin_role_permission` VALUES ('1', '38');
INSERT INTO `admin_role_permission` VALUES ('1', '39');
INSERT INTO `admin_role_permission` VALUES ('1', '40');
INSERT INTO `admin_role_permission` VALUES ('1', '41');
INSERT INTO `admin_role_permission` VALUES ('1', '42');
INSERT INTO `admin_role_permission` VALUES ('1', '43');
INSERT INTO `admin_role_permission` VALUES ('1', '44');
INSERT INTO `admin_role_permission` VALUES ('1', '45');
INSERT INTO `admin_role_permission` VALUES ('1', '46');
INSERT INTO `admin_role_permission` VALUES ('1', '47');
INSERT INTO `admin_role_permission` VALUES ('1', '48');
INSERT INTO `admin_role_permission` VALUES ('1', '49');
INSERT INTO `admin_role_permission` VALUES ('1', '50');
INSERT INTO `admin_role_permission` VALUES ('1', '51');
INSERT INTO `admin_role_permission` VALUES ('1', '52');
INSERT INTO `admin_role_permission` VALUES ('1', '53');
INSERT INTO `admin_role_permission` VALUES ('1', '54');
INSERT INTO `admin_role_permission` VALUES ('1', '55');
INSERT INTO `admin_role_permission` VALUES ('1', '56');
INSERT INTO `admin_role_permission` VALUES ('1', '57');
INSERT INTO `admin_role_permission` VALUES ('1', '58');
INSERT INTO `admin_role_permission` VALUES ('1', '59');
INSERT INTO `admin_role_permission` VALUES ('1', '60');
INSERT INTO `admin_role_permission` VALUES ('1', '61');
INSERT INTO `admin_role_permission` VALUES ('1', '62');
INSERT INTO `admin_role_permission` VALUES ('1', '63');
INSERT INTO `admin_role_permission` VALUES ('1', '64');
INSERT INTO `admin_role_permission` VALUES ('1', '65');
INSERT INTO `admin_role_permission` VALUES ('1', '66');
INSERT INTO `admin_role_permission` VALUES ('1', '67');
INSERT INTO `admin_role_permission` VALUES ('1', '68');
INSERT INTO `admin_role_permission` VALUES ('1', '69');
INSERT INTO `admin_role_permission` VALUES ('1', '70');
INSERT INTO `admin_role_permission` VALUES ('1', '71');
INSERT INTO `admin_role_permission` VALUES ('1', '72');
INSERT INTO `admin_role_permission` VALUES ('1', '73');
INSERT INTO `admin_role_permission` VALUES ('1', '74');
INSERT INTO `admin_role_permission` VALUES ('1', '75');
INSERT INTO `admin_role_permission` VALUES ('1', '76');
INSERT INTO `admin_role_permission` VALUES ('1', '77');
INSERT INTO `admin_role_permission` VALUES ('1', '78');
INSERT INTO `admin_role_permission` VALUES ('1', '79');
INSERT INTO `admin_role_permission` VALUES ('1', '80');
INSERT INTO `admin_role_permission` VALUES ('1', '81');
INSERT INTO `admin_role_permission` VALUES ('1', '82');
INSERT INTO `admin_role_permission` VALUES ('1', '83');
INSERT INTO `admin_role_permission` VALUES ('1', '84');
INSERT INTO `admin_role_permission` VALUES ('1', '85');
INSERT INTO `admin_role_permission` VALUES ('1', '86');
INSERT INTO `admin_role_permission` VALUES ('1', '87');
INSERT INTO `admin_role_permission` VALUES ('1', '88');
INSERT INTO `admin_role_permission` VALUES ('1', '89');
INSERT INTO `admin_role_permission` VALUES ('1', '90');
INSERT INTO `admin_role_permission` VALUES ('1', '91');
INSERT INTO `admin_role_permission` VALUES ('1', '92');
INSERT INTO `admin_role_permission` VALUES ('1', '93');
INSERT INTO `admin_role_permission` VALUES ('1', '94');
INSERT INTO `admin_role_permission` VALUES ('1', '95');
INSERT INTO `admin_role_permission` VALUES ('1', '96');
INSERT INTO `admin_role_permission` VALUES ('1', '97');
INSERT INTO `admin_role_permission` VALUES ('1', '98');
INSERT INTO `admin_role_permission` VALUES ('1', '99');
INSERT INTO `admin_role_permission` VALUES ('1', '100');
INSERT INTO `admin_role_permission` VALUES ('1', '101');
INSERT INTO `admin_role_permission` VALUES ('1', '102');
INSERT INTO `admin_role_permission` VALUES ('1', '103');
INSERT INTO `admin_role_permission` VALUES ('1', '104');
INSERT INTO `admin_role_permission` VALUES ('1', '105');
INSERT INTO `admin_role_permission` VALUES ('1', '106');
INSERT INTO `admin_role_permission` VALUES ('1', '107');
INSERT INTO `admin_role_permission` VALUES ('1', '108');
INSERT INTO `admin_role_permission` VALUES ('1', '109');
INSERT INTO `admin_role_permission` VALUES ('1', '110');
INSERT INTO `admin_role_permission` VALUES ('1', '111');
INSERT INTO `admin_role_permission` VALUES ('1', '112');
INSERT INTO `admin_role_permission` VALUES ('1', '113');
INSERT INTO `admin_role_permission` VALUES ('1', '114');
INSERT INTO `admin_role_permission` VALUES ('1', '115');
INSERT INTO `admin_role_permission` VALUES ('1', '116');
INSERT INTO `admin_role_permission` VALUES ('1', '117');
INSERT INTO `admin_role_permission` VALUES ('1', '118');
INSERT INTO `admin_role_permission` VALUES ('1', '119');
INSERT INTO `admin_role_permission` VALUES ('1', '120');
INSERT INTO `admin_role_permission` VALUES ('1', '121');
INSERT INTO `admin_role_permission` VALUES ('1', '122');
INSERT INTO `admin_role_permission` VALUES ('1', '123');
INSERT INTO `admin_role_permission` VALUES ('1', '124');
INSERT INTO `admin_role_permission` VALUES ('1', '125');
INSERT INTO `admin_role_permission` VALUES ('1', '126');
INSERT INTO `admin_role_permission` VALUES ('1', '128');
INSERT INTO `admin_role_permission` VALUES ('1', '129');
INSERT INTO `admin_role_permission` VALUES ('1', '130');
INSERT INTO `admin_role_permission` VALUES ('1', '131');
INSERT INTO `admin_role_permission` VALUES ('1', '132');
INSERT INTO `admin_role_permission` VALUES ('1', '133');
INSERT INTO `admin_role_permission` VALUES ('1', '134');
INSERT INTO `admin_role_permission` VALUES ('1', '135');
INSERT INTO `admin_role_permission` VALUES ('1', '136');
INSERT INTO `admin_role_permission` VALUES ('1', '137');
INSERT INTO `admin_role_permission` VALUES ('1', '138');
INSERT INTO `admin_role_permission` VALUES ('1', '139');
INSERT INTO `admin_role_permission` VALUES ('1', '140');
INSERT INTO `admin_role_permission` VALUES ('1', '141');
INSERT INTO `admin_role_permission` VALUES ('1', '142');
INSERT INTO `admin_role_permission` VALUES ('1', '143');
INSERT INTO `admin_role_permission` VALUES ('1', '144');
INSERT INTO `admin_role_permission` VALUES ('1', '145');
INSERT INTO `admin_role_permission` VALUES ('1', '146');
INSERT INTO `admin_role_permission` VALUES ('1', '147');
INSERT INTO `admin_role_permission` VALUES ('1', '148');
INSERT INTO `admin_role_permission` VALUES ('1', '149');
INSERT INTO `admin_role_permission` VALUES ('1', '150');
INSERT INTO `admin_role_permission` VALUES ('1', '151');
INSERT INTO `admin_role_permission` VALUES ('1', '152');
INSERT INTO `admin_role_permission` VALUES ('1', '153');
INSERT INTO `admin_role_permission` VALUES ('1', '154');
INSERT INTO `admin_role_permission` VALUES ('1', '155');
INSERT INTO `admin_role_permission` VALUES ('1', '156');
INSERT INTO `admin_role_permission` VALUES ('1', '158');
INSERT INTO `admin_role_permission` VALUES ('1', '159');
INSERT INTO `admin_role_permission` VALUES ('1', '160');
INSERT INTO `admin_role_permission` VALUES ('1', '161');
INSERT INTO `admin_role_permission` VALUES ('1', '162');
INSERT INTO `admin_role_permission` VALUES ('1', '164');
INSERT INTO `admin_role_permission` VALUES ('1', '165');
INSERT INTO `admin_role_permission` VALUES ('1', '167');
INSERT INTO `admin_role_permission` VALUES ('1', '168');
INSERT INTO `admin_role_permission` VALUES ('1', '169');
INSERT INTO `admin_role_permission` VALUES ('1', '170');
INSERT INTO `admin_role_permission` VALUES ('1', '171');
INSERT INTO `admin_role_permission` VALUES ('1', '172');
INSERT INTO `admin_role_permission` VALUES ('1', '173');
INSERT INTO `admin_role_permission` VALUES ('1', '174');
INSERT INTO `admin_role_permission` VALUES ('1', '176');
INSERT INTO `admin_role_permission` VALUES ('1', '177');
INSERT INTO `admin_role_permission` VALUES ('1', '178');
INSERT INTO `admin_role_permission` VALUES ('1', '179');
INSERT INTO `admin_role_permission` VALUES ('1', '180');
INSERT INTO `admin_role_permission` VALUES ('1', '181');
INSERT INTO `admin_role_permission` VALUES ('1', '182');
INSERT INTO `admin_role_permission` VALUES ('1', '183');
INSERT INTO `admin_role_permission` VALUES ('1', '184');
INSERT INTO `admin_role_permission` VALUES ('1', '185');
INSERT INTO `admin_role_permission` VALUES ('1', '186');
INSERT INTO `admin_role_permission` VALUES ('1', '187');

