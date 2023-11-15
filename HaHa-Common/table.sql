CREATE TABLE `tb_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL COMMENT '用户昵称',
  `sex` char(1) NOT NULL DEFAULT '0' COMMENT '性别：1=男，2=女，0=其他',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号码',
  `login_name` varchar(100) NOT NULL COMMENT '登录账号名',
  `login_password` varchar(100) NOT NULL COMMENT '登录密码',
  `img_url` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户头像url',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `tb_file` (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(100) NOT NULL COMMENT '文件名',
  `file_size` varchar(20) NOT NULL COMMENT '文件大小',
  `type` varchar(20) NOT NULL COMMENT '文件类型',
  `path` varchar(200) NOT NULL COMMENT '文件路径',
  `url` varchar(200) DEFAULT NULL COMMENT '文件访问url',
  `encrypted` int NOT NULL COMMENT '是否加密：1=是，0=否',
  `deleted` int NOT NULL COMMENT '是否已删除：1=是，0=否',
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_file` (`file_name`,`file_size`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件表';

CREATE TABLE `tb_articles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '文章标题',
  `content` text NOT NULL COMMENT '文章内容',
  `creator` int NOT NULL COMMENT '创作者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` int NOT NULL COMMENT '文章状态：1=草稿，2=已发布，3=已下架，4=已删除',
  `view` int NOT NULL DEFAULT '1' COMMENT '文章可见：1=公开所有人可见，2=仅个人可见，3=仅指定好友可见',
  `publish_time` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章内容表';