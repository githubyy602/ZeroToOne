CREATE TABLE `tb_user` (
    `id` int NOT NULL AUTO_INCREMENT,
    `user_name` varchar(100) NOT NULL COMMENT '用户昵称',
    `sex` char(1) NOT NULL DEFAULT '0' COMMENT '性别：1=男，2=女，0=其他',
    `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
    `phone` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '手机号码',
    `login_name` varchar(100) NOT NULL COMMENT '登录账号名',
    `login_password` varchar(100) NOT NULL COMMENT '登录密码',
    `img_id` int DEFAULT NULL COMMENT '头像文件id',
    `img_url` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户头像url',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户表';

CREATE TABLE `tb_file` (
   `id` int NOT NULL AUTO_INCREMENT,
   `file_name` varchar(100) NOT NULL COMMENT '文件名',
   `file_size` varchar(20) NOT NULL COMMENT '文件大小',
   `type` varchar(20) NOT NULL COMMENT '文件类型',
   `path` varchar(200) NOT NULL COMMENT '文件路径',
   `url` varchar(200) DEFAULT NULL COMMENT '文件访问url',
   `encrypted` int NOT NULL DEFAULT '0' COMMENT '是否加密：1=是，0=否',
   `deleted` int NOT NULL DEFAULT '0' COMMENT '是否已删除：1=是，0=否',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `un_file` (`file_name`,`file_size`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='文件表';

CREATE TABLE `tb_articles` (
   `id` int NOT NULL AUTO_INCREMENT,
   `title` varchar(100) NOT NULL COMMENT '文章标题',
   `content` text NOT NULL COMMENT '文章内容',
   `cover_img_id` int DEFAULT NULL COMMENT '文章封面图片id',
   `creator` int NOT NULL COMMENT '创作者',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `status` int NOT NULL COMMENT '文章状态：1=草稿，2=已发布，3=已下架，4=已删除',
   `view` int NOT NULL DEFAULT '1' COMMENT '文章可见：1=公开所有人可见，2=仅个人可见，3=仅指定好友可见',
   `publish_time` timestamp NULL DEFAULT NULL COMMENT '发布时间',
   `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='文章内容表';

CREATE TABLE `tb_login_log` (
    `id` int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL COMMENT '登录用户id',
    `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    `login_result` int NOT NULL DEFAULT '0' COMMENT '登录结果：1=成功，2=失败',
    `client_type` int NOT NULL DEFAULT '1' COMMENT '登录端类型：1=web，2=安卓，3=ios',
    `login_ip` varchar(100) DEFAULT NULL COMMENT '登录ip',
    `sys_version` varchar(30) DEFAULT NULL COMMENT '登录系统版本',
    `logout_time` timestamp NULL DEFAULT NULL COMMENT '登出时间',
    `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='登录日志表';