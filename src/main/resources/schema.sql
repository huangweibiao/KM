-- KM/Wiki 知识管理系统数据库初始化脚本
-- 适用于 MySQL 8.0

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS km_wiki CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE km_wiki;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(64) NOT NULL COMMENT '用户名',
    `email` VARCHAR(128) NOT NULL COMMENT '邮箱',
    `password_hash` VARCHAR(255) NOT NULL COMMENT '密码哈希',
    `avatar_url` VARCHAR(512) DEFAULT NULL COMMENT '头像URL',
    `nickname` VARCHAR(64) DEFAULT NULL COMMENT '昵称',
    `dept_id` BIGINT DEFAULT NULL COMMENT '所属部门ID',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0禁用,1正常',
    `last_login_at` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    KEY `idx_dept_id` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS `role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `code` VARCHAR(64) NOT NULL COMMENT '角色代码: ADMIN, EDITOR, VIEWER',
    `name` VARCHAR(64) NOT NULL COMMENT '角色名称',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '描述',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS `user_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- 权限表
CREATE TABLE IF NOT EXISTS `permission` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    `code` VARCHAR(128) NOT NULL COMMENT '权限代码: wiki:create, page:edit',
    `name` VARCHAR(64) NOT NULL COMMENT '权限名称',
    `resource_type` VARCHAR(32) DEFAULT NULL COMMENT '资源类型: WIKI, PAGE, COMMENT',
    `action` VARCHAR(32) DEFAULT NULL COMMENT '操作: CREATE, READ, UPDATE, DELETE',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS `role_permission` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `permission_id` BIGINT NOT NULL COMMENT '权限ID',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`),
    KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关联表';

-- 部门表
CREATE TABLE IF NOT EXISTS `department` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '部门ID',
    `name` VARCHAR(64) NOT NULL COMMENT '部门名称',
    `parent_id` BIGINT DEFAULT NULL COMMENT '父部门ID',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';

-- 知识库表
CREATE TABLE IF NOT EXISTS `wiki` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '知识库ID',
    `name` VARCHAR(128) NOT NULL COMMENT '知识库名称',
    `slug` VARCHAR(128) NOT NULL COMMENT 'URL标识符',
    `description` TEXT DEFAULT NULL COMMENT '描述',
    `logo_url` VARCHAR(512) DEFAULT NULL COMMENT 'Logo URL',
    `owner_id` BIGINT NOT NULL COMMENT '创建者/所有者ID',
    `visibility` TINYINT NOT NULL DEFAULT 1 COMMENT '可见性: 1公开,2内部,3私有',
    `is_archived` TINYINT NOT NULL DEFAULT 0 COMMENT '是否归档: 0否,1是',
    `page_count` INT DEFAULT 0 COMMENT '页面数量',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_slug` (`slug`),
    KEY `idx_owner_id` (`owner_id`),
    KEY `idx_visibility` (`visibility`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识库表';

-- 知识库成员表
CREATE TABLE IF NOT EXISTS `wiki_member` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `wiki_id` BIGINT NOT NULL COMMENT '知识库ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_in_wiki` VARCHAR(32) NOT NULL COMMENT '角色: OWNER, ADMIN, EDITOR, VIEWER',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_wiki_user` (`wiki_id`, `user_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识库成员表';

-- 分类表
CREATE TABLE IF NOT EXISTS `category` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `wiki_id` BIGINT NOT NULL COMMENT '所属知识库ID',
    `parent_id` BIGINT DEFAULT NULL COMMENT '父分类ID',
    `name` VARCHAR(64) NOT NULL COMMENT '分类名称',
    `slug` VARCHAR(128) NOT NULL COMMENT 'URL标识符',
    `sort_order` INT DEFAULT 0 COMMENT '排序顺序',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_wiki_id` (`wiki_id`),
    KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分类表';

-- 页面表
CREATE TABLE IF NOT EXISTS `page` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '页面ID',
    `wiki_id` BIGINT NOT NULL COMMENT '所属知识库ID',
    `category_id` BIGINT DEFAULT NULL COMMENT '分类ID',
    `parent_page_id` BIGINT DEFAULT NULL COMMENT '父页面ID',
    `title` VARCHAR(256) NOT NULL COMMENT '页面标题',
    `slug` VARCHAR(256) NOT NULL COMMENT 'URL标识符',
    `content` LONGTEXT DEFAULT NULL COMMENT '页面内容',
    `content_format` VARCHAR(16) DEFAULT 'markdown' COMMENT '内容格式: markdown, html',
    `type` VARCHAR(16) DEFAULT 'doc' COMMENT '类型: doc/wiki/faq',
    `version` INT NOT NULL DEFAULT 1 COMMENT '当前版本号',
    `author_id` BIGINT NOT NULL COMMENT '创建者ID',
    `last_editor_id` BIGINT DEFAULT NULL COMMENT '最后编辑者ID',
    `status` TINYINT DEFAULT 0 COMMENT '状态: 0草稿/1已发布',
    `published_at` DATETIME DEFAULT NULL COMMENT '发布时间',
    `view_count` INT DEFAULT 0 COMMENT '浏览次数',
    `like_count` INT DEFAULT 0 COMMENT '点赞数',
    `is_deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_wiki_id` (`wiki_id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_parent_page_id` (`parent_page_id`),
    KEY `idx_author_id` (`author_id`),
    KEY `idx_status` (`status`),
    KEY `idx_is_deleted` (`is_deleted`),
    FULLTEXT KEY `ft_title_content` (`title`, `content`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='页面表';

-- 页面版本表
CREATE TABLE IF NOT EXISTS `page_version` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '版本ID',
    `page_id` BIGINT NOT NULL COMMENT '页面ID',
    `version` INT NOT NULL COMMENT '版本号',
    `title` VARCHAR(256) NOT NULL COMMENT '标题快照',
    `content` LONGTEXT NOT NULL COMMENT '内容快照',
    `editor_id` BIGINT NOT NULL COMMENT '编辑者ID',
    `change_note` VARCHAR(512) DEFAULT NULL COMMENT '修改备注',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_page_version` (`page_id`, `version`),
    KEY `idx_editor_id` (`editor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='页面版本表';

-- 标签表
CREATE TABLE IF NOT EXISTS `tag` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '标签ID',
    `wiki_id` BIGINT DEFAULT NULL COMMENT '所属知识库ID(全局为空)',
    `name` VARCHAR(64) NOT NULL COMMENT '标签名称',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_wiki_id` (`wiki_id`),
    KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

-- 页面标签关联表
CREATE TABLE IF NOT EXISTS `page_tag` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `page_id` BIGINT NOT NULL COMMENT '页面ID',
    `tag_id` BIGINT NOT NULL COMMENT '标签ID',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_page_tag` (`page_id`, `tag_id`),
    KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='页面标签关联表';

-- 评论表
CREATE TABLE IF NOT EXISTS `comment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `page_id` BIGINT NOT NULL COMMENT '页面ID',
    `user_id` BIGINT NOT NULL COMMENT '评论者ID',
    `parent_id` BIGINT DEFAULT NULL COMMENT '父评论ID',
    `content` TEXT NOT NULL COMMENT '评论内容',
    `like_count` INT DEFAULT 0 COMMENT '点赞数',
    `is_deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_page_id` (`page_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- 收藏表
CREATE TABLE IF NOT EXISTS `favorite` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `page_id` BIGINT NOT NULL COMMENT '页面ID',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_page` (`user_id`, `page_id`),
    KEY `idx_page_id` (`page_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏表';

-- 关注表
CREATE TABLE IF NOT EXISTS `watch` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关注ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `page_id` BIGINT NOT NULL COMMENT '页面ID',
    `watch_type` VARCHAR(16) DEFAULT 'ALL' COMMENT '类型: ALL, COMMENT, EDIT',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_page` (`user_id`, `page_id`),
    KEY `idx_page_id` (`page_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='关注表';

-- 审计日志表
CREATE TABLE IF NOT EXISTS `audit_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `user_id` BIGINT DEFAULT NULL COMMENT '操作用户ID',
    `username` VARCHAR(64) DEFAULT NULL COMMENT '用户名快照',
    `action` VARCHAR(64) NOT NULL COMMENT '操作类型: CREATE, UPDATE, DELETE',
    `resource_type` VARCHAR(32) NOT NULL COMMENT '资源类型: PAGE, WIKI, COMMENT',
    `resource_id` BIGINT DEFAULT NULL COMMENT '资源ID',
    `old_value` TEXT DEFAULT NULL COMMENT '旧值(JSON)',
    `new_value` TEXT DEFAULT NULL COMMENT '新值(JSON)',
    `ip_address` VARCHAR(45) DEFAULT NULL COMMENT 'IP地址',
    `user_agent` VARCHAR(512) DEFAULT NULL COMMENT 'User Agent',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_resource` (`resource_type`, `resource_id`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='审计日志表';

-- 通知表
CREATE TABLE IF NOT EXISTS `notification` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '通知ID',
    `user_id` BIGINT NOT NULL COMMENT '接收用户ID',
    `type` VARCHAR(32) NOT NULL COMMENT '类型',
    `title` VARCHAR(256) NOT NULL COMMENT '通知标题',
    `content` TEXT DEFAULT NULL COMMENT '通知内容',
    `link_url` VARCHAR(512) DEFAULT NULL COMMENT '跳转链接',
    `is_read` TINYINT DEFAULT 0 COMMENT '是否已读',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_is_read` (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知表';

-- 附件表
CREATE TABLE IF NOT EXISTS `attachment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '附件ID',
    `page_id` BIGINT NOT NULL COMMENT '关联页面ID',
    `name` VARCHAR(256) NOT NULL COMMENT '文件名',
    `original_name` VARCHAR(256) NOT NULL COMMENT '原始文件名',
    `file_size` BIGINT NOT NULL COMMENT '文件大小（字节）',
    `file_type` VARCHAR(64) NOT NULL COMMENT '文件类型（MIME）',
    `storage_path` VARCHAR(512) NOT NULL COMMENT '存储路径',
    `url` VARCHAR(512) NOT NULL COMMENT '访问URL',
    `uploader_id` BIGINT NOT NULL COMMENT '上传者ID',
    `is_deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_page_id` (`page_id`),
    KEY `idx_uploader_id` (`uploader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='附件表';

-- 插入默认角色数据
INSERT INTO `role` (`code`, `name`, `description`) VALUES
('ADMIN', '管理员', '系统管理员，拥有所有权限'),
('EDITOR', '编辑者', '可以创建和编辑内容'),
('VIEWER', '查看者', '只能查看内容')
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`), `description` = VALUES(`description`);

-- 插入默认权限数据
INSERT INTO `permission` (`code`, `name`, `resource_type`, `action`) VALUES
('wiki:create', '创建知识库', 'WIKI', 'CREATE'),
('wiki:read', '查看知识库', 'WIKI', 'READ'),
('wiki:update', '更新知识库', 'WIKI', 'UPDATE'),
('wiki:delete', '删除知识库', 'WIKI', 'DELETE'),
('page:create', '创建页面', 'PAGE', 'CREATE'),
('page:read', '查看页面', 'PAGE', 'READ'),
('page:update', '更新页面', 'PAGE', 'UPDATE'),
('page:delete', '删除页面', 'PAGE', 'DELETE'),
('comment:create', '发表评论', 'COMMENT', 'CREATE'),
('comment:read', '查看评论', 'COMMENT', 'READ'),
('comment:delete', '删除评论', 'COMMENT', 'DELETE')
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

-- 为角色分配权限
-- ADMIN拥有所有权限
INSERT INTO `role_permission` (`role_id`, `permission_id`)
SELECT 1, id FROM `permission`
ON DUPLICATE KEY UPDATE `role_id` = VALUES(`role_id`);

-- EDITOR拥有查看、创建、更新权限
INSERT INTO `role_permission` (`role_id`, `permission_id`)
SELECT 2, id FROM `permission` WHERE `action` IN ('READ', 'CREATE', 'UPDATE')
ON DUPLICATE KEY UPDATE `role_id` = VALUES(`role_id`);

-- VIEWER只有查看权限
INSERT INTO `role_permission` (`role_id`, `permission_id`)
SELECT 3, id FROM `permission` WHERE `action` = 'READ'
ON DUPLICATE KEY UPDATE `role_id` = VALUES(`role_id`);
