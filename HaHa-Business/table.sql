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