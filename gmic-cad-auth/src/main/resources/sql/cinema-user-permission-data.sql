SET FOREIGN_KEY_CHECKS=0;
-------------------------------------
-------------------------------------
-- ----------------------------
-- Records of perm_group
-- ----------------------------


INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('100000000000000107', '100000000000000010', '100000000000000101', 'adverting.management', '广告管理');
INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('100000000000000108', '100000000000000010', '100000000000000107', 'advertingPlan.management', '广告计划');
INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('100000000000000109', '100000000000000010', '100000000000000107', 'planningOrder.management', '广告订单');
INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('100000000000000110', '100000000000000010', '100000000000000107', 'orderTask.management', '执行订单');
INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('100000000000000111', '100000000000000010', '100000000000000107', 'advertingMaterial.management', '素材管理');

 INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
 ('100000000000000112', '100000000000000010', '100000000000000107', 'Org.management', '广告主管理');

  INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
 ('100000000000000117', '100000000000000010', '100000000000000107', 'billingStrategy.management', '收费策略');


INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('100000000000000113', '100000000000000010', '100000000000000101', 'cinema.management', '影院管理');
INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('100000000000000114', '100000000000000010', '100000000000000113', 'filmSchedule.management', '影片排期');
INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('100000000000000115', '100000000000000010', '100000000000000113', 'film.management', '影片管理');

INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('100000000000000116', '100000000000000010', '100000000000000113', 'cinemaOrg.management', '影院管理');

-- ----------------------------
-- Records of perm
-- ----------------------------
INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000310001', '100000000000000112', '查看广告主', 'advertiser.view'),
('100000000000310002', '100000000000000112', '编辑广告主', 'advertiser.edit'),
('100000000000310003', '100000000000000112', '删除广告主', 'advertiser.del'),
('100000000000310004', '100000000000000112', '新增广告主', 'advertiser.new');



INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030010', '100000000000000108', '查看广告计划', 'advertingplan.view'),
('100000000000030011', '100000000000000108', '新建广告计划', 'advertingplan.new'),
('100000000000030012', '100000000000000108', '编辑广告计划', 'advertingplan.edit'),
('100000000000030013', '100000000000000108', '删除广告计划', 'advertingplan.delete');

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030014', '100000000000000109', '查看广告订单', 'order.view'),
('100000000000030015', '100000000000000109', '新建广告订单', 'order.new'),
('100000000000030016', '100000000000000109', '编辑广告订单', 'order.edit'),
('100000000000030017', '100000000000000109', '删除广告订单', 'order.delete');

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030018', '100000000000000110', '查看执行订单', 'ordertask.view'),
('100000000000030019', '100000000000000110', '新建执行订单', 'ordertask.new'),
('100000000000030020', '100000000000000110', '编辑执行订单', 'ordertask.edit'),
('100000000000030021', '100000000000000110', '删除执行订单', 'ordertask.delete');

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030022', '100000000000000111', '查看素材管理', 'advertingmaterial.view'),
('100000000000030023', '100000000000000111', '新建素材管理', 'advertingmaterial.new'),
('100000000000030024', '100000000000000111', '编辑素材管理', 'advertingmaterial.edit'),
('100000000000030025', '100000000000000111', '删除素材管理', 'advertingmaterial.delete');


INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030026', '100000000000000114', '查看排期管理', 'filmschedule.view'),
('100000000000030027', '100000000000000114', '新建排期管理', 'filmschedule.new'),
('100000000000030028', '100000000000000114', '编辑排期管理', 'filmschedule.edit'),
('100000000000030029', '100000000000000114', '删除排期管理', 'filmschedule.delete');

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030030', '100000000000000115', '查看影片管理', 'film.view'),
('100000000000030031', '100000000000000115', '新建影片管理', 'film.new'),
('100000000000030032', '100000000000000115', '编辑影片管理', 'film.edit'),
('100000000000030033', '100000000000000115', '删除影片管理', 'film.delete');

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030034', '100000000000000116', '查看影院管理', 'cinema.view'),
('100000000000030035', '100000000000000116', '新建影院管理', 'cinema.new'),
('100000000000030036', '100000000000000116', '编辑影院管理', 'cinema.edit'),
('100000000000030037', '100000000000000116', '删除影院管理', 'cinema.delete');

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030038', '100000000000000117', '查看策略管理', 'billingstrategy.view'),
('100000000000030039', '100000000000000117', '新建策略管理', 'billingstrategy.new'),
('100000000000030040', '100000000000000117', '编辑策略管理', 'billingstrategy.edit'),
('100000000000030041', '100000000000000117', '删除策略管理', 'billingstrategy.delete');

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030042', '100000000000000108', '审核广告计划', 'advertingplan.review'),
('100000000000030043', '100000000000000108', '下单广告计划', 'advertingplan.order');

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030044', '100000000000000109', '提交订单结案', 'order.provide'),
('100000000000030045', '100000000000000109', '确认订单', 'order.confirm'),
('100000000000030046', '100000000000000109', '结算订单', 'order.finish');

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030047', '100000000000000110', '下发子订单', 'ordertask.deliver'),
('100000000000030048', '100000000000000110', '取消子订单', 'ordertask.cancel'),
('100000000000030049', '100000000000000110', '上传子订单结案文件', 'ordertask.provide'),
('100000000000030050', '100000000000000110', '确认子订单结案', 'ordertask.verify');

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030051', '100000000000000111', '商务编辑素材管理', 'advertingmaterialsw.edit');

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030052', '100000000000000112', '分配店小二', 'advertiser.setAssistant');

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES
('100000000000030053', '100000000000000116', '分配商务', 'cinema.setAssistant');

INSERT INTO `sys_perm`(`id`, `group_id`, `identifier`, `name`, `tag`) VALUES
('100000000000030059', '100000000000000110', 'ordertask.confirm', '确认查收子订单', '0');


INSERT INTO `sys_perm_group`(`id`, `org_id`, `pid`, `identifier`, `name`) VALUES ('100000000000000118', '100000000000000010', '100000000000000101', 'reportStatistics.management', '报表统计');
INSERT INTO `sys_perm_group`(`id`, `org_id`, `pid`, `identifier`, `name`) VALUES ('100000000000000119', '100000000000000010', '100000000000000118', 'reportStatisticschild.management', '报表统计');


INSERT INTO `sys_perm`(`id`, `group_id`, `identifier`, `name`, `tag`) VALUES ('100000000000030054', '100000000000000119', 'advertiserReportStatistics.view', '查看广告主统计', '0');
INSERT INTO `sys_perm`(`id`, `group_id`, `identifier`, `name`, `tag`) VALUES ('100000000000030055', '100000000000000119', 'cinemaReportStatistics.view', '查看影院统计', '0');
INSERT INTO `sys_perm`(`id`, `group_id`, `identifier`, `name`, `tag`) VALUES ('100000000000030056', '100000000000000119', 'platformReportStatistics.view', '平台统计总览', '0');
INSERT INTO `sys_perm`(`id`, `group_id`, `identifier`, `name`, `tag`) VALUES ('100000000000030057', '100000000000000119', 'platformOrderReportStatistics.view', '平台订单统计', '0');
INSERT INTO `sys_perm`(`id`, `group_id`, `identifier`, `name`, `tag`) VALUES ('100000000000030058', '100000000000000119', 'platformRankReportStatistics.view', '平台排名统计', '0');

INSERT INTO `sys_perm_url` ( `perm_id`, `url`, `org_id`, `org_tag`) VALUES ( '100000000000030054', '/api/adm/stat/meta/template/advertiser', NULL, NULL);
INSERT INTO `sys_perm_url` ( `perm_id`, `url`, `org_id`, `org_tag`) VALUES ( '100000000000030055', '/api/adm/stat/meta/template/cinema', NULL, NULL);
INSERT INTO `sys_perm_url` ( `perm_id`, `url`, `org_id`, `org_tag`) VALUES ( '100000000000030056', '/api/adm/stat/meta/template/plaformTotal', NULL, NULL);
INSERT INTO `sys_perm_url` ( `perm_id`, `url`, `org_id`, `org_tag`) VALUES ( '100000000000030057', '/api/adm/stat/meta/template/plaformOrder', NULL, NULL);
INSERT INTO `sys_perm_url` ( `perm_id`, `url`, `org_id`, `org_tag`) VALUES ( '100000000000030058', '/api/adm/stat/meta/template/plaformRank', NULL, NULL);


INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES ('100000000000030060', '100000000000000108', '导出广告计划', 'advertingplan.export');
INSERT INTO `sys_perm_url` ( `perm_id`, `url`, `org_id`, `org_tag`) VALUES ( '100000000000030060', '/api/io/excel/export/advertingPlan', NULL, NULL);

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES ('100000000000030061', '100000000000000109', '导出广告计划', 'order.exoprt');
INSERT INTO `sys_perm_url` ( `perm_id`, `url`, `org_id`, `org_tag`) VALUES ( '100000000000030061', '/api/io/excel/export/advertingPlan', NULL, NULL);

INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES ('100000000000030062', '100000000000000110', '导出执行订单', 'ordertask.export');
INSERT INTO `sys_perm_url` ( `perm_id`, `url`, `org_id`, `org_tag`) VALUES ( '100000000000030062', '/api/io/excel/export/orderTask', NULL, NULL);

INSERT INTO `sys_perm`(`id`, `group_id`, `identifier`, `name`, `tag`) VALUES ('100000000000030063', '100000000000000119', 'advertiserReportStatistics.export', '导出广告主统计', '0');
INSERT INTO `sys_perm_url` ( `perm_id`, `url`, `org_id`, `org_tag`) VALUES ( '100000000000030063', '/api/io/excel/export/advertiserReport', NULL, NULL);


INSERT INTO `sys_perm` (`id`, `group_id`, `name`, `identifier`) VALUES ('100000000000030064', '100000000000000112', '导出广告主', 'advertiser.export');
INSERT INTO `sys_perm_url` ( `perm_id`, `url`, `org_id`, `org_tag`) VALUES ( '100000000000030064', '/api/io/excel/export/advertiser', NULL, NULL);