SET FOREIGN_KEY_CHECKS=0;

INSERT INTO `sys_role` (`id`, `org_id`, `sort_order`, `pid`, `name`, `tips`, `version`, `delete_flag`,`role_code`, `made_by`,`user_type`) VALUES
('5', '100000000000000010', '1', NULL, '店小二', '复制广告主管理的角色', NULL, '0', '104', 'SYSTEM','2');
INSERT INTO `sys_role` (`id`, `org_id`, `sort_order`, `pid`, `name`, `tips`, `version`, `delete_flag`,`role_code`, `made_by`,`user_type`) VALUES
('4', '100000000000000010', '1', NULL, '商务', '负责影院管理的角色', NULL, '0', '106', 'SYSTEM','2');
INSERT INTO `sys_role` (`id`, `org_id`, `sort_order`, `pid`, `name`, `tips`, `version`, `delete_flag`,`role_code`, `made_by`,`user_type`) VALUES
('3', '100000000000000010', '1', NULL, '店小二主管', '各个店小二的管理人', NULL, '0', '103', 'SYSTEM','3');
INSERT INTO `sys_role` (`id`, `org_id`, `sort_order`, `pid`, `name`, `tips`, `version`, `delete_flag`,`role_code`, `made_by`,`user_type`) VALUES
('2', '100000000000000010', '1', NULL, '商务主管', '各个商务的管理人', NULL, '0', '105', 'SYSTEM','3');

INSERT INTO `sys_role` (`id`, `org_id`, `sort_order`, `pid`, `name`, `tips`, `version`, `role_code`, `made_by`, `delete_flag`,`user_type`) VALUES
 ('6', '100000000000000010', '1', NULL, '平台管理员', '平台管理员', NULL, '101', 'SYSTEM', '0','101');

INSERT INTO `sys_role` (`id`, `org_id`, `sort_order`, `pid`, `name`, `tips`, `version`, `role_code`, `made_by`, `delete_flag`,`user_type`) VALUES
 ('7', '100000000000000010', '1', NULL, '广告主-审核前', '广告主-审核前', NULL, '200', 'SYSTEM', '0',NULL);

INSERT INTO `sys_role` (`id`, `org_id`, `sort_order`, `pid`, `name`, `tips`, `version`, `role_code`, `made_by`, `delete_flag`,`user_type`) VALUES
 ('8', '100000000000000010', '1', NULL, '广告主-审核后', '广告主-审核后', NULL, '201', 'SYSTEM', '0',NULL);
INSERT INTO `sys_role` (`id`, `org_id`, `sort_order`, `pid`, `name`, `tips`, `version`, `role_code`, `made_by`, `delete_flag`,`user_type`) VALUES
 ('9', '100000000000000010', '1', NULL, '影院管理人', '影院', NULL, '301', 'SYSTEM', '0',NULL);




INSERT INTO `t_sys_org` (`id`, `pid`, `name`, `org_code`, `full_name`, `node_level`, `left_num`, `right_num`, `note`, `status`, `org_type`, `create_time`, `update_time`, `b_type`) VALUES
('100000000000000001', NULL, '可圈点科技有限公司 ', 'platform', '广州可圈点信息科技有限公司', '1', '1', '10', '总公司', 'NORMAL', '0', '2020-09-15 14:23:23', '2020-09-15 16:01:45','SYSTEM');
INSERT INTO `t_sys_org` (`id`, `pid`, `name`, `org_code`, `full_name`, `node_level`, `left_num`, `right_num`, `note`, `status`, `org_type`, `create_time`, `update_time`, `b_type`) VALUES
('100000000000000010', '100000000000000001', '星鱼影城智慧云平台 ', 'System', '星鱼影城智慧云平台', '2', '2', '9', '总公司', 'NORMAL', '0', '2020-09-15 14:23:23', '2020-09-15 16:01:45','SYSTEM');
INSERT INTO `t_sys_org` (`id`, `pid`, `name`, `org_code`, `full_name`, `node_level`, `left_num`, `right_num`, `note`, `status`, `org_type`, `create_time`, `update_time`, `b_type`) VALUES
('100000000000000040', '100000000000000010', '广告主 ', 'Advertiser', '广告主', '3', '3', '4', '总公司', 'NORMAL', '0', '2020-09-15 14:23:23', '2020-09-15 16:01:45','USER');
INSERT INTO `t_sys_org` (`id`, `pid`, `name`, `org_code`, `full_name`, `node_level`, `left_num`, `right_num`, `note`, `status`, `org_type`, `create_time`, `update_time`, `b_type`) VALUES
('100000000000000050', '100000000000000010', '各大影院 ', 'Cinema', '各大影院', '3', '5', '6', '总公司', 'NORMAL', '0', '2020-09-15 14:23:23', '2020-09-15 16:01:45','USER');
INSERT INTO `t_sys_org` (`id`, `pid`, `name`, `org_code`, `full_name`, `node_level`, `left_num`, `right_num`, `note`, `status`, `org_type`, `create_time`, `update_time`, `b_type`) VALUES
('100000000000000060', '100000000000000010', '各大院线', 'CinemaLine', '各大院线', '3', '7', '8', '总公司', 'NORMAL', '0', '2020-09-15 14:23:23', '2020-09-15 16:01:45','USER');





INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES
 ('876708082437197831', '876708082437197830', '6');




------------------------------------- 院线数据 -------------------------------------
-- ----------------------------
-- Records of ca_cinema_line
-- ----------------------------
INSERT INTO `ca_cinema_line` VALUES (1, '万达院线', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (2, '广东大地', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (3, '上海联和', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (4, '中影南方新干线', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (5, '中影数字', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (6, '中影星美', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (7, '广州金逸珠江', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (8, '横店影视', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (9, '江苏幸福蓝海', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (10, '华夏联合', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (11, '四川太平洋', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (12, '浙江时代', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (13, '重庆保利万和', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (14, '北京红鲤鱼', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (15, '河南奥斯卡', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (16, '北京新影联', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (17, '湖北银兴', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (18, '长城沃美', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (19, '辽宁中影北方', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (20, '浙江星光', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (21, '完美世界', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (22, '武汉天河影业', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (23, '明星时代', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (24, '深影橙天', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (25, '湖南潇湘', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (26, '上海大光明', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (27, '博纳院线', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (28, '福建中兴', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (29, '山东奥卡新世纪', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (30, '湖南楚湘', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (31, '河北中联影业', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (32, '温州雁荡', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (33, '吉林吉影', '100000000000000060', '2020-09-21 18:17:09', '2020-09-21 18:17:09', NULL);
INSERT INTO `ca_cinema_line` VALUES (34, '江西星河', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (35, '北京九州中原', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (36, '江苏东方', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (37, '华人院线', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (38, '世纪环球', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (39, '鲁信院线', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (40, '中广国际', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (41, '内蒙古民族', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (42, '华夏新华大地', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (43, '贵州星空', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (44, '新疆华夏天山', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (45, '天津银光', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (46, '四川峨眉', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (47, '甘肃新视界', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (48, '西安长安', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (49, '上海弘歌', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);
INSERT INTO `ca_cinema_line` VALUES (50, '新疆电影公司', '100000000000000060', '2020-09-21 18:17:10', '2020-09-21 18:17:10', NULL);




