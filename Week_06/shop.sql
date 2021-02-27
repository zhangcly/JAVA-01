CREATE TABLE `tb_item` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `item_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `item_discription` varchar(300) DEFAULT NULL COMMENT '商品描述',
  `item_type` int(11) DEFAULT NULL COMMENT '商品类型',
  `price` varchar(100) DEFAULT NULL COMMENT '价格',
  `thumbnail_url` varchar(200) DEFAULT NULL COMMENT '缩略图地址',
  `brand` varchar(50) DEFAULT NULL COMMENT '品牌',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺id',
  `item_status` tinyint(4) DEFAULT NULL COMMENT '商品状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `tb_item_snapshot` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `item_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `item_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `item_discription` varchar(300) DEFAULT NULL COMMENT '商品描述',
  `item_type` int(11) DEFAULT NULL COMMENT '商品类型',
  `price` varchar(100) DEFAULT NULL COMMENT '价格',
  `brand` varchar(50) DEFAULT NULL COMMENT '品牌',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `tb_order` (
  `id` bigint(20) NOT NULL,
  `order_status` tinyint(4) DEFAULT NULL COMMENT '订单状态',
  `pay_type` tinyint(4) DEFAULT NULL COMMENT '支付方式',
  `original_price` varchar(20) DEFAULT NULL COMMENT '原价',
  `actual_price` varchar(20) DEFAULT NULL COMMENT '实际支付价格',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `item_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `item_snapshot_id` bigint(20) DEFAULT NULL COMMENT '商品快照id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `tb_order_payment` (
  `id` int(11) NOT NULL COMMENT '主键',
  `pay_type` tinyint(4) DEFAULT NULL,
  `pay_id` varchar(200) DEFAULT NULL,
  `collection_account` varchar(200) DEFAULT NULL COMMENT '收款账户',
  `payer_id` varchar(200) DEFAULT NULL COMMENT '支付宝、微信等中的支付人id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `pay_amount` varchar(255) DEFAULT NULL COMMENT '支付金额',
  `pay_status` tinyint(4) DEFAULT NULL COMMENT '支付状态（支付成功，退款中，已退款）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `user_type` int(11) DEFAULT NULL COMMENT '用户类型',
  `sign` varchar(100) DEFAULT NULL COMMENT '个性签名',
  `nick_name` varchar(40) DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  `head_pic_url` varchar(200) DEFAULT NULL COMMENT '头像图片地址',
  `account_status` tinyint(4) DEFAULT NULL COMMENT '账户状态',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_modify_pwd_time` datetime DEFAULT NULL COMMENT '上次修改密码时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
