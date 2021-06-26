SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Records of perm_group
-- ----------------------------

INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('100000000000000107', '100000000000000010', '100000000000000101', 'adverting.management', '广告管理');

 INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
 ('100000000000000112', '100000000000000010', '100000000000000107', 'Org.management', '广告主管理');

-- ----------------------------
-- Records of perm
-- ----------------------------
INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000310001', '100000000000000112', '查看广告主', 'advertiser.view'),
('100000000000310002', '100000000000000112', '编辑广告主', 'advertiser.edit'),
('100000000000310003', '100000000000000112', '删除广告主', 'advertiser.del'),
('100000000000310004', '100000000000000112', '新增广告主', 'advertiser.new');

