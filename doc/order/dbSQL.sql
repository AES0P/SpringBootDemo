-- 商品表
create table 'product_info'(
	'product_id' VARCHAR(32) not null,
	'product_name' VARCHAR(64) not null comment '商品名称',
	'product_price' DECIMAL(8,2) not null comment '单价',
	'product_stock' int not null COMMENT '库存',
	'product_description' VARCHAR(64) COMMENT '描述',
	'product_icon' VARCHAR(512) COMMENT '小图',
	'category_type' is not null COMMENT '类型编号',
	'create_time' TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	'update_time' TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP 
		COMMENT '修改时间',	
	PRIMARY KEY('product_id')
) COMMENT '商品表';

-- 类目表
CREATE TABLE 'product_category'(
	'category_id' int not null auto_increment,
	'category_name' VARCHAR(64) not null comment '类目名字',
	'category_type' int not null COMMENT '类目编号',
	'create_time' TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	'update_time' TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP 
		COMMENT '修改时间',	
	PRIMARY KEY('category_id'),
	UNIQUE key 'uqe_category_type' ('category_type')
)COMMENT '类目表';

-- 订单表
create table 'order_master'(
	'order_id' VARCHAR(32) not null,
	'buyer_name' VARCHAR(32) not null COMMENT '买家名字',
	'buyer_phone' VARCHAR(32) not null COMMENT '买家电话',
	'buyer_address' VARCHAR(128) not null COMMENT '买家地址',
	'buyer_openid' VARCHAR(64) not null COMMENT '买家微信openId',
	'order_amount' DECIMAL(8,2) not null COMMENT '订单总金额',
	'order_status' TINYINT(3) not null DEFAULT '0' COMMENT '订单状态，默认0新下单',
	'pay_status' TINYINT(3) not null DEFAULT '0' COMMENT '订单状态，默认0未支付',
	'create_time' TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	'update_time' TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP 
		COMMENT '修改时间',	
	PRIMARY KEY('order_id'),
	key 'idx_buyer_openid' ('buyer_openid')
)COMMENT '订单表';

-- 订单详情表
create table 'order_detail'(
	'detail_id' VARCHAR(32) not null,
	'order_id' VARCHAR(32) not null,
	'product_id' VARCHAR(32) not null,
	'product_name' VARCHAR(64) not null COMMENT '商品名称',
	'product_price' VARCHAR(8,2) not null COMMENT '商品价格',
	'product_quantity' VARCHAR(32) not null COMMENT '商品数量',
	'product_icon' VARCHAR(32) not null COMMENT '商品小图',
	'create_time' TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	'update_time' TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP 
		COMMENT '修改时间',	
	PRIMARY KEY('detail_id'),
	key 'idx_order_id' ('order_id')
)COMMENT '订单详情表'


