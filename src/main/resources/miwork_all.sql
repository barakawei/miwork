# ************************************************************
# Sequel Pro SQL dump
# Version 3408
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.28)
# Database: miwork
# Generation Time: 2013-05-05 02:24:12 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table data_dict
# ------------------------------------------------------------

DROP TABLE IF EXISTS `data_dict`;

CREATE TABLE `data_dict` (
  `id` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `type_name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `data_dict` WRITE;
/*!40000 ALTER TABLE `data_dict` DISABLE KEYS */;

INSERT INTO `data_dict` (`id`, `type`, `type_name`, `value`)
VALUES
	('1','position','部位','男装'),
	('10','name','名称','内胆'),
	('11','name','名称','胆插袋'),
	('12','name','名称','手机袋'),
	('13','name','名称','领罗纹'),
	('14','name','名称','帽松紧'),
	('15','name','名称','腰松紧'),
	('16','name','名称','袖口松紧'),
	('17','name','名称','帽绳'),
	('18','name','名称','下摆松紧'),
	('19','name','名称','下摆绳'),
	('2','position','部位','男上衣'),
	('20','name','名称','脚口'),
	('21','name','名称','口袋1'),
	('22','name','名称','口袋2'),
	('23','name','名称','脚口松紧'),
	('24','name','名称','胆腰松紧'),
	('25','name','名称','胆脚口松紧'),
	('26','name','名称','胆绳'),
	('27','material','材质','尼龙'),
	('28','material','材质','树脂'),
	('29','material','材质','铜'),
	('3','position','部位','男裤'),
	('30','spec','规格','3#*1'),
	('31','spec','规格','3#*2'),
	('32','spec','规格','3#*3'),
	('33','spec','规格','3#*4'),
	('34','spec','规格','3#*5'),
	('35','spec','规格','5#*1'),
	('36','spec','规格','5#*2'),
	('37','spec','规格','5#*3'),
	('38','spec','规格','5#*4'),
	('39','spec','规格','5#*5'),
	('4','position','部位','女装'),
	('40','spec','规格','8#*1'),
	('41','spec','规格','8#*2'),
	('42','spec','规格','8#*3'),
	('43','spec','规格','8#*4'),
	('44','spec','规格','8#*5'),
	('45','model','型号','145'),
	('46','model','型号','150'),
	('47','model','型号','155'),
	('48','model','型号','160'),
	('49','model','型号','165'),
	('5','position','部位','女上衣'),
	('50','model','型号','170'),
	('51','model','型号','175'),
	('52','model','型号','180'),
	('53','model','型号','185'),
	('54','model','型号','190'),
	('55','model','型号','195'),
	('56','model','型号','4XS'),
	('57','model','型号','3XS'),
	('58','model','型号','2XS'),
	('59','model','型号','XS'),
	('6','position','部位','女裤'),
	('60','model','型号','S'),
	('61','model','型号','M'),
	('62','model','型号','L'),
	('63','model','型号','XL'),
	('64','model','型号','2XL'),
	('65','model','型号','3XL'),
	('66','model','型号','4XL'),
	('67','model','型号','5XL'),
	('7','name','名称','门襟'),
	('8','name','名称','胸袋'),
	('9','name','名称','插袋');

/*!40000 ALTER TABLE `data_dict` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table goods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` varchar(255) NOT NULL,
  `actual_consume` varchar(255) DEFAULT NULL,
  `actual_loss` varchar(255) DEFAULT NULL,
  `actual_purchasing_count` varchar(255) DEFAULT NULL,
  `actual_use` varchar(255) DEFAULT NULL,
  `actual_width` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `composition` varchar(255) DEFAULT NULL,
  `confirm_use` varchar(255) DEFAULT NULL,
  `consume` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discharge_spec` varchar(255) DEFAULT NULL,
  `exceed_use` varchar(255) DEFAULT NULL,
  `expected_arrival_time` datetime DEFAULT NULL,
  `loss` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `need_add` varchar(255) DEFAULT NULL,
  `order_count` varchar(255) DEFAULT NULL,
  `ori_price` varchar(255) DEFAULT NULL,
  `plan_entry_count` varchar(255) DEFAULT NULL,
  `plan_entry_time` datetime DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `purchasing_count` varchar(255) DEFAULT NULL,
  `shrinkage` varchar(255) DEFAULT NULL,
  `specification` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `warehouse_count` varchar(255) DEFAULT NULL,
  `width` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table organization
# ------------------------------------------------------------

DROP TABLE IF EXISTS `organization`;

CREATE TABLE `organization` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `level` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4644ED33C4F1902` (`parent_id`),
  CONSTRAINT `FK4644ED33C4F1902` FOREIGN KEY (`parent_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table organization_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `organization_role`;

CREATE TABLE `organization_role` (
  `org_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  PRIMARY KEY (`org_id`,`role_id`),
  KEY `FKCD3E9402A4B8FFF9` (`role_id`),
  KEY `FKCD3E940254C91088` (`org_id`),
  CONSTRAINT `FKCD3E940254C91088` FOREIGN KEY (`org_id`) REFERENCES `organization` (`id`),
  CONSTRAINT `FKCD3E9402A4B8FFF9` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table privilege
# ------------------------------------------------------------

DROP TABLE IF EXISTS `privilege`;

CREATE TABLE `privilege` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `enable` tinyint(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `priority` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table purchasing
# ------------------------------------------------------------

DROP TABLE IF EXISTS `purchasing`;

CREATE TABLE `purchasing` (
  `id` varchar(255) NOT NULL,
  `actual_shrinkage` varchar(255) DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `confirm_date` datetime DEFAULT NULL,
  `confirm_name` varchar(255) DEFAULT NULL,
  `confirm_time` datetime DEFAULT NULL,
  `count_detail` text,
  `discharge_recognition` varchar(255) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `ongoing` tinyint(1) DEFAULT NULL,
  `order_count` varchar(255) DEFAULT NULL,
  `order_name` varchar(255) DEFAULT NULL,
  `order_number` varchar(255) DEFAULT NULL,
  `plan_date` datetime DEFAULT NULL,
  `plan_discharge` varchar(255) DEFAULT NULL,
  `planning_user_name` varchar(255) DEFAULT NULL,
  `serial_number` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `type_number` varchar(255) DEFAULT NULL,
  `zipper_shrinkage` varchar(255) DEFAULT NULL,
  `planning_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK11BBE57E7F6524F7` (`planning_user_id`),
  CONSTRAINT `FK11BBE57E7F6524F7` FOREIGN KEY (`planning_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table purchasing_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `purchasing_detail`;

CREATE TABLE `purchasing_detail` (
  `id` varchar(255) NOT NULL,
  `actual_entry_count` varchar(255) DEFAULT NULL,
  `actual_entry_time` datetime DEFAULT NULL,
  `actual_purchasing_count` varchar(255) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `expected_arrival_time` datetime DEFAULT NULL,
  `order_count` varchar(255) DEFAULT NULL,
  `plan_entry_count` varchar(255) DEFAULT NULL,
  `plan_entry_time` datetime DEFAULT NULL,
  `plan_purchasing_count` varchar(255) DEFAULT NULL,
  `process_instance_id` varchar(255) DEFAULT NULL,
  `qualified` tinyint(1) DEFAULT NULL,
  `quality_remark` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `review` tinyint(1) DEFAULT NULL,
  `shrinkage` varchar(255) DEFAULT NULL,
  `special_requirements` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `warehouse_count` varchar(255) DEFAULT NULL,
  `goods_id` varchar(255) DEFAULT NULL,
  `purchasing_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK75B6A63280387FDB` (`goods_id`),
  KEY `FK75B6A632168C7B9` (`purchasing_id`),
  KEY `FK75B6A63249E3C3D9` (`user_id`),
  CONSTRAINT `FK75B6A63249E3C3D9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK75B6A632168C7B9` FOREIGN KEY (`purchasing_id`) REFERENCES `purchasing` (`id`),
  CONSTRAINT `FK75B6A63280387FDB` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `enable` tinyint(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`id`, `create_date`, `description`, `enable`, `name`, `type`)
VALUES
	('1',NULL,'管理员',1,'role_admin',NULL),
	('2',NULL,'计划员',1,'role_planning',NULL),
	('3',NULL,'采购员',1,'role_purchasing',NULL),
	('4',NULL,'库管员',1,'role_warehouse',NULL),
	('5',NULL,'质检员',1,'role_quality',NULL),
	('6',NULL,'技术员',1,'role_technolog',NULL),
	('7',NULL,'领导',1,'role_leader',NULL),
	('8',NULL,'生产员',1,'role_product',NULL),
	('9',NULL,'群众',1,'role_normal',NULL);

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role_privilege
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role_privilege`;

CREATE TABLE `role_privilege` (
  `role_id` varchar(255) NOT NULL,
  `privilege_id` varchar(255) NOT NULL,
  KEY `FK45FBD6284E61A57B` (`privilege_id`),
  KEY `FK45FBD628A4B8FFF9` (`role_id`),
  CONSTRAINT `FK45FBD628A4B8FFF9` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK45FBD6284E61A57B` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `account` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enable` tinyint(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `org_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK36EBCB54C91088` (`org_id`),
  CONSTRAINT `FK36EBCB54C91088` FOREIGN KEY (`org_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `account`, `create_date`, `email`, `enable`, `name`, `password`, `phone`, `telephone`, `org_id`)
VALUES
	('1','admin',NULL,'',0,'管理员','111111','','',NULL),
	('2','jihua',NULL,'',0,'计划员','111111','','',NULL),
	('3','caigou',NULL,'',0,'采购员','111111','','',NULL),
	('4','kuguan',NULL,'',0,'库管员','111111','','',NULL),
	('5','zhijian',NULL,'',0,'质检员','111111','','',NULL),
	('6','jishu',NULL,'',0,'技术员','111111','','',NULL),
	('7','lingdao',NULL,'',0,'领导','111111','','',NULL),
	('8','shengchan',NULL,'',0,'生产员','111111','','',NULL);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  KEY `FK143BF46AA4B8FFF9` (`role_id`),
  KEY `FK143BF46A49E3C3D9` (`user_id`),
  CONSTRAINT `FK143BF46A49E3C3D9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK143BF46AA4B8FFF9` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;

INSERT INTO `user_role` (`user_id`, `role_id`)
VALUES
	('1','1'),
	('2','2'),
	('3','3'),
	('5','5'),
	('6','6'),
	('7','7'),
	('8','8'),
	('4','4');

/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table zipper
# ------------------------------------------------------------

DROP TABLE IF EXISTS `zipper`;

CREATE TABLE `zipper` (
  `id` varchar(255) NOT NULL,
  `material` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `spec` varchar(255) DEFAULT NULL,
  `zipper_count` text,
  `purchasing_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD62B7A5C168C7B9` (`purchasing_id`),
  CONSTRAINT `FKD62B7A5C168C7B9` FOREIGN KEY (`purchasing_id`) REFERENCES `purchasing` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
