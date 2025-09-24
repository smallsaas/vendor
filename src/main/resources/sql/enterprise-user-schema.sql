SET FOREIGN_KEY_CHECKS=0;

-- 企业表  旧广告主表改
DROP TABLE IF EXISTS `ca_advertiser`;
CREATE TABLE `ca_advertiser` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`company_name` VARCHAR (50) DEFAULT NULL COMMENT '广告主名',
`contact_name` VARCHAR(20) DEFAULT NULL COMMENT '联系人名字',
`contact_phone` VARCHAR(20) NOT NULL COMMENT '联系人电话',
`contact_id` BIGINT(20)  COMMENT '联系人id  有id的话按id的信息来 为空按照上面的字段为主',
`balance` decimal(10,2) DEFAULT 0 COMMENT '金额',
`come_from` int(10) DEFAULT 0 COMMENT '来源 0-系统创建 1-用户注册',
`status` VARCHAR(20) DEFAULT 'PENDING_APPROVAL' COMMENT '状态   PASS-已审核 PENDING_APPROVAL-待审核 REFUSE-已拒绝',
`address` VARCHAR(255) DEFAULT NULL COMMENT '所在地区',
`assistant_id` BIGINT(20) DEFAULT NULL COMMENT '店小二id',
`org_id` bigint(20) DEFAULT NULL,
`province` VARCHAR(20) DEFAULT NULL COMMENT '所在省',
`city` VARCHAR(20) DEFAULT NULL COMMENT '所在城市',
`district` VARCHAR(20) DEFAULT NULL COMMENT '市区',
`id_card` VARCHAR(20) DEFAULT NULL COMMENT '身份证',
`id_card_url` VARCHAR(255) DEFAULT NULL COMMENT '身份证图片',
`id_card_back_url` VARCHAR(255) DEFAULT NULL COMMENT '身份证图片',
`business_license_prove` VARCHAR(255) DEFAULT NULL COMMENT '营业执照资质证明',
`business_license_phone` VARCHAR(255) DEFAULT NULL COMMENT '营业执照照片',
`industry` VARCHAR(255) DEFAULT NULL COMMENT '所在行业',
`x_industry` VARCHAR(255) DEFAULT NULL COMMENT '所在行业 x行业',
`l_industry` VARCHAR(255) DEFAULT NULL COMMENT '所在行业 l大分类',
`m_industry` VARCHAR(255) DEFAULT NULL COMMENT '所在行业 m中分类',
`login_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最近登录时间',
`create_time` datetime DEFAULT CURRENT_TIMESTAMP,
`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
`note` text COMMENT '说明 备注',
`avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像',
PRIMARY KEY (`id`),
UNIQUE KEY `contact_phone` (`contact_phone`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业表';


DROP TABLE IF EXISTS `ca_advertiser_operate_log`;
CREATE TABLE `ca_advertiser_operate_log` (
  `id` bigint(20)  NOT NULL AUTO_INCREMENT,
  `note` text DEFAULT NULL COMMENT '备注',
  `advertiser_id` bigint(20) DEFAULT NULL COMMENT '企业id',
  `status` varchar(50) DEFAULT NULL COMMENT '修改后状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业审核记录';

