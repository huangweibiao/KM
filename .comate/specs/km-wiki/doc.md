# KM/Wiki 知识管理系统 - 详细设计文档

## 一、项目概述

### 1.1 系统目标
- 统一知识沉淀（文档、FAQ、经验、流程）
- 高效检索（全文搜索 + 语义搜索）
- 支持协作（评论、编辑、版本）
- 权限与安全控制
- 可扩展（未来拆分微服务）

### 1.2 技术栈
- **后端**: Spring Boot 3.5.11 + JPA + Spring Security + JWT
- **前端**: Vue 3 + Element Plus+typescript
- **数据库**: MySQL 8.0 + Redis（缓存/会话）
- **全文检索**: MySQL全文索引（初期）
- **文件存储**: 本地存储（初期）

### 1.3 核心功能模块
1. 用户/权限/角色管理（IAM）
2. 知识库/空间管理
3. 文档/Wiki/FAQ页面管理
4. 版本管理
5. 标签/分类管理
6. 评论/协作讨论
7. 全文搜索
8. 审核与流程管理
9. 通知管理
10. 文件与附件管理
11. 操作审计日志

---

## 二、架构设计

### 2.1 分层架构
```
前端（Vue 3 + Element Plus）
        ↓
API网关/Controller层
        ↓
应用服务层（Service）
        ↓
领域层（Entity/Domain）
        ↓
基础设施层（Repository/Mapper）
        ↓
MySQL / Redis
```

### 2.2 包结构
```
com.km.wiki/
├── config/           # 配置类
├── controller/       # REST API控制器
├── service/          # 业务逻辑层
│   ├── impl/         # 服务实现
├── entity/           # 实体类
├── dto/              # 数据传输对象
├── vo/               # 视图对象
├── mapper/           # MyBatis-Plus Mapper
├── repository/       # 数据访问层
├── security/         # 安全相关
├── common/           # 通用工具
│   ├── result/       # 统一响应结果
│   ├── exception/    # 异常处理
│   └── utils/        # 工具类
└── WikiApplication.java
```

---

## 三、数据库设计

### 3.1 用户与权限模块

#### 表1: `user` (用户表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 用户ID |
| username | VARCHAR(64) | NOT NULL, UNIQUE | 用户名 |
| email | VARCHAR(128) | NOT NULL, UNIQUE | 邮箱 |
| password_hash | VARCHAR(255) | NOT NULL | 密码哈希 |
| avatar_url | VARCHAR(512) | | 头像URL |
| nickname | VARCHAR(64) | | 昵称 |
| dept_id | BIGINT | | 所属部门ID |
| status | TINYINT | NOT NULL, DEFAULT 1 | 状态: 0禁用,1正常 |
| last_login_at | DATETIME | | 最后登录时间 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |

#### 表2: `role` (角色表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 角色ID |
| code | VARCHAR(64) | NOT NULL, UNIQUE | 角色代码 |
| name | VARCHAR(64) | NOT NULL | 角色名称 |
| description | VARCHAR(255) | | 描述 |
| created_at | DATETIME | NOT NULL | 创建时间 |

#### 表3: `user_role` (用户角色关联表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 关联ID |
| user_id | BIGINT | NOT NULL, FK | 用户ID |
| role_id | BIGINT | NOT NULL, FK | 角色ID |
| created_at | DATETIME | NOT NULL | 创建时间 |

#### 表4: `permission` (权限表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 权限ID |
| code | VARCHAR(128) | NOT NULL, UNIQUE | 权限代码 |
| name | VARCHAR(64) | NOT NULL | 权限名称 |
| resource_type | VARCHAR(32) | | 资源类型 |
| action | VARCHAR(32) | | 操作 |
| created_at | DATETIME | NOT NULL | 创建时间 |

#### 表5: `role_permission` (角色权限关联表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 关联ID |
| role_id | BIGINT | NOT NULL, FK | 角色ID |
| permission_id | BIGINT | NOT NULL, FK | 权限ID |
| created_at | DATETIME | NOT NULL | 创建时间 |

#### 表6: `department` (部门表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 部门ID |
| name | VARCHAR(64) | NOT NULL | 部门名称 |
| parent_id | BIGINT | FK | 父部门ID |
| sort_order | INT | DEFAULT 0 | 排序 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |

### 3.2 知识库模块

#### 表7: `wiki` (知识库表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 知识库ID |
| name | VARCHAR(128) | NOT NULL | 知识库名称 |
| slug | VARCHAR(128) | NOT NULL, UNIQUE | URL标识符 |
| description | TEXT | | 描述 |
| logo_url | VARCHAR(512) | | Logo URL |
| owner_id | BIGINT | NOT NULL, FK | 创建者/所有者ID |
| visibility | TINYINT | NOT NULL, DEFAULT 1 | 可见性: 1公开,2内部,3私有 |
| is_archived | TINYINT | NOT NULL, DEFAULT 0 | 是否归档 |
| page_count | INT | DEFAULT 0 | 页面数量 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |

#### 表8: `wiki_member` (知识库成员表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 关联ID |
| wiki_id | BIGINT | NOT NULL, FK | 知识库ID |
| user_id | BIGINT | NOT NULL, FK | 用户ID |
| role_in_wiki | VARCHAR(32) | NOT NULL | 角色: OWNER, ADMIN, EDITOR, VIEWER |
| created_at | DATETIME | NOT NULL | 创建时间 |

### 3.3 文档/页面模块

#### 表9: `category` (分类表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 分类ID |
| wiki_id | BIGINT | NOT NULL, FK | 所属知识库ID |
| parent_id | BIGINT | FK | 父分类ID |
| name | VARCHAR(64) | NOT NULL | 分类名称 |
| slug | VARCHAR(128) | NOT NULL | URL标识符 |
| sort_order | INT | DEFAULT 0 | 排序顺序 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |

#### 表10: `page` (页面表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 页面ID |
| wiki_id | BIGINT | NOT NULL, FK | 所属知识库ID |
| category_id | BIGINT | FK | 分类ID |
| parent_page_id | BIGINT | FK | 父页面ID |
| title | VARCHAR(256) | NOT NULL | 页面标题 |
| slug | VARCHAR(256) | NOT NULL | URL标识符 |
| content | LONGTEXT | | 页面内容 |
| content_format | VARCHAR(16) | DEFAULT 'markdown' | 内容格式 |
| type | VARCHAR(16) | DEFAULT 'doc' | 类型: doc/wiki/faq |
| version | INT | NOT NULL, DEFAULT 1 | 当前版本号 |
| author_id | BIGINT | NOT NULL, FK | 创建者ID |
| last_editor_id | BIGINT | FK | 最后编辑者ID |
| status | TINYINT | DEFAULT 0 | 状态: 0草稿/1已发布 |
| published_at | DATETIME | | 发布时间 |
| view_count | INT | DEFAULT 0 | 浏览次数 |
| like_count | INT | DEFAULT 0 | 点赞数 |
| is_deleted | TINYINT | DEFAULT 0 | 逻辑删除 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |

#### 表11: `page_version` (页面版本表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 版本ID |
| page_id | BIGINT | NOT NULL, FK | 页面ID |
| version | INT | NOT NULL | 版本号 |
| title | VARCHAR(256) | NOT NULL | 标题快照 |
| content | LONGTEXT | NOT NULL | 内容快照 |
| editor_id | BIGINT | NOT NULL, FK | 编辑者ID |
| change_note | VARCHAR(512) | | 修改备注 |
| created_at | DATETIME | NOT NULL | 创建时间 |

#### 表12: `tag` (标签表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 标签ID |
| wiki_id | BIGINT | FK | 所属知识库ID |
| name | VARCHAR(64) | NOT NULL | 标签名称 |
| created_at | DATETIME | NOT NULL | 创建时间 |

#### 表13: `page_tag` (页面标签关联表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 关联ID |
| page_id | BIGINT | NOT NULL, FK | 页面ID |
| tag_id | BIGINT | NOT NULL, FK | 标签ID |
| created_at | DATETIME | NOT NULL | 创建时间 |

### 3.4 协作与互动模块

#### 表14: `comment` (评论表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 评论ID |
| page_id | BIGINT | NOT NULL, FK | 页面ID |
| user_id | BIGINT | NOT NULL, FK | 评论者ID |
| parent_id | BIGINT | FK | 父评论ID |
| content | TEXT | NOT NULL | 评论内容 |
| like_count | INT | DEFAULT 0 | 点赞数 |
| is_deleted | TINYINT | DEFAULT 0 | 逻辑删除 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |

#### 表15: `favorite` (收藏表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 收藏ID |
| user_id | BIGINT | NOT NULL, FK | 用户ID |
| page_id | BIGINT | NOT NULL, FK | 页面ID |
| created_at | DATETIME | NOT NULL | 创建时间 |

#### 表16: `watch` (关注表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 关注ID |
| user_id | BIGINT | NOT NULL, FK | 用户ID |
| page_id | BIGINT | NOT NULL, FK | 页面ID |
| watch_type | VARCHAR(16) | DEFAULT 'ALL' | 类型: ALL, COMMENT, EDIT |
| created_at | DATETIME | NOT NULL | 创建时间 |

### 3.5 搜索与审计模块

#### 表17: `audit_log` (审计日志表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 日志ID |
| user_id | BIGINT | FK | 操作用户ID |
| username | VARCHAR(64) | | 用户名快照 |
| action | VARCHAR(64) | NOT NULL | 操作类型 |
| resource_type | VARCHAR(32) | NOT NULL | 资源类型 |
| resource_id | BIGINT | | 资源ID |
| old_value | TEXT | | 旧值(JSON) |
| new_value | TEXT | | 新值(JSON) |
| ip_address | VARCHAR(45) | | IP地址 |
| user_agent | VARCHAR(512) | | User Agent |
| created_at | DATETIME | NOT NULL | 创建时间 |

#### 表18: `notification` (通知表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 通知ID |
| user_id | BIGINT | NOT NULL, FK | 接收用户ID |
| type | VARCHAR(32) | NOT NULL | 类型 |
| title | VARCHAR(256) | NOT NULL | 通知标题 |
| content | TEXT | | 通知内容 |
| link_url | VARCHAR(512) | | 跳转链接 |
| is_read | TINYINT | DEFAULT 0 | 是否已读 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |

### 3.6 文件与附件模块

#### 表19: `attachment` (附件表)
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 附件ID |
| page_id | BIGINT | NOT NULL, FK | 关联页面ID |
| name | VARCHAR(256) | NOT NULL | 文件名 |
| original_name | VARCHAR(256) | NOT NULL | 原始文件名 |
| file_size | BIGINT | NOT NULL | 文件大小 |
| file_type | VARCHAR(64) | NOT NULL | 文件类型 |
| storage_path | VARCHAR(512) | NOT NULL | 存储路径 |
| url | VARCHAR(512) | NOT NULL | 访问URL |
| uploader_id | BIGINT | NOT NULL, FK | 上传者ID |
| is_deleted | TINYINT | DEFAULT 0 | 逻辑删除 |
| created_at | DATETIME | NOT NULL | 创建时间 |

---

## 四、API设计

### 4.1 用户与权限API
```
POST   /api/auth/login              - 用户登录
POST   /api/auth/register           - 用户注册
POST   /api/auth/logout             - 用户登出
GET    /api/auth/me                 - 获取当前用户信息

GET    /api/users                   - 获取用户列表
POST   /api/users                   - 创建用户
GET    /api/users/{id}              - 获取用户详情
PUT    /api/users/{id}              - 更新用户
DELETE /api/users/{id}              - 禁用/删除用户

GET    /api/roles                   - 获取角色列表
POST   /api/roles                   - 创建角色
PUT    /api/roles/{id}/permissions  - 分配角色权限
```

### 4.2 知识库API
```
GET    /api/wikis                   - 获取知识库列表
POST   /api/wikis                   - 创建知识库
GET    /api/wikis/{id}              - 获取知识库详情
PUT    /api/wikis/{id}              - 更新知识库
DELETE /api/wikis/{id}              - 归档/删除知识库

GET    /api/wikis/{id}/members      - 获取成员列表
POST   /api/wikis/{id}/members      - 添加成员
DELETE /api/wikis/{id}/members/{userId} - 移除成员
```

### 4.3 页面API
```
GET    /api/wikis/{wikiId}/pages           - 获取页面列表(树形)
POST   /api/wikis/{wikiId}/pages           - 创建页面
GET    /api/pages/{id}                     - 获取页面内容
PUT    /api/pages/{id}                     - 更新页面
DELETE /api/pages/{id}                     - 删除页面(软删除)

GET    /api/pages/{id}/versions            - 获取版本历史
POST   /api/pages/{id}/versions/{v}/restore - 恢复版本

POST   /api/pages/{id}/favorite            - 收藏/取消收藏
POST   /api/pages/{id}/watch               - 关注/取消关注
```

### 4.4 标签与分类API
```
GET    /api/wikis/{wikiId}/tags       - 获取标签列表
POST   /api/wikis/{wikiId}/tags       - 创建标签
DELETE /api/tags/{id}                 - 删除标签
POST   /api/pages/{id}/tags           - 绑定标签
DELETE /api/pages/{id}/tags/{tagId}   - 解绑标签

GET    /api/wikis/{wikiId}/categories - 获取分类列表
POST   /api/wikis/{wikiId}/categories - 创建分类
PUT    /api/categories/{id}           - 更新分类
DELETE /api/categories/{id}           - 删除分类
```

### 4.5 评论API
```
GET    /api/pages/{pageId}/comments   - 获取评论列表
POST   /api/pages/{pageId}/comments   - 发表评论
PUT    /api/comments/{id}             - 编辑评论
DELETE /api/comments/{id}             - 删除评论
```

### 4.6 搜索API
```
GET    /api/search?q={keyword}&wikiId={wikiId} - 关键词搜索
GET    /api/search/suggest?q={keyword}         - 搜索建议
```

### 4.7 文件附件API
```
POST   /api/pages/{id}/attachments    - 上传附件
GET    /api/pages/{id}/attachments    - 获取附件列表
DELETE /api/attachments/{id}          - 删除附件
GET    /api/attachments/{id}/download - 下载附件
```

### 4.8 通知API
```
GET    /api/notifications             - 获取通知列表
PUT    /api/notifications/{id}/read   - 标记通知已读
PUT    /api/notifications/read-all    - 标记所有已读
```

---

## 五、核心业务逻辑

### 5.1 页面版本管理
- 每次UPDATE页面操作自动创建page_version快照
- 版本号自增，保留最近100个版本
- 支持版本恢复，恢复后生成新版本

### 5.2 权限控制矩阵
| 操作 | OWNER | ADMIN | EDITOR | VIEWER |
|------|-------|-------|--------|--------|
| 查看页面 | ✅ | ✅ | ✅ | ✅ |
| 创建页面 | ✅ | ✅ | ✅ | ❌ |
| 编辑自己页面 | ✅ | ✅ | ✅ | ❌ |
| 编辑他人页面 | ✅ | ✅ | ❌ | ❌ |
| 删除页面 | ✅ | ✅ | ❌ | ❌ |
| 管理成员 | ✅ | ✅ | ❌ | ❌ |
| 删除知识库 | ✅ | ❌ | ❌ | ❌ |

### 5.3 搜索架构
- 关键词搜索：MySQL全文索引，标题权重>内容权重
- 支持分页和排序

---

## 六、项目文件清单

### 6.1 后端文件

#### 配置类
- `config/SecurityConfig.java` - Spring Security配置
- `config/MyBatisPlusConfig.java` - MyBatis-Plus配置
- `config/CorsConfig.java` - 跨域配置
- `config/WebConfig.java` - Web配置

#### 安全相关
- `security/JwtTokenProvider.java` - JWT令牌提供者
- `security/JwtAuthenticationFilter.java` - JWT认证过滤器
- `security/UserDetailsServiceImpl.java` - 用户详情服务
- `security/CustomUserDetails.java` - 自定义用户详情

#### 实体类 (Entity)
- `entity/User.java` - 用户实体
- `entity/Role.java` - 角色实体
- `entity/UserRole.java` - 用户角色关联
- `entity/Permission.java` - 权限实体
- `entity/RolePermission.java` - 角色权限关联
- `entity/Department.java` - 部门实体
- `entity/Wiki.java` - 知识库实体
- `entity/WikiMember.java` - 知识库成员
- `entity/Category.java` - 分类实体
- `entity/Page.java` - 页面实体
- `entity/PageVersion.java` - 页面版本
- `entity/Tag.java` - 标签实体
- `entity/PageTag.java` - 页面标签关联
- `entity/Comment.java` - 评论实体
- `entity/Favorite.java` - 收藏实体
- `entity/Watch.java` - 关注实体
- `entity/AuditLog.java` - 审计日志
- `entity/Notification.java` - 通知实体
- `entity/Attachment.java` - 附件实体

#### DTO/VO
- `dto/UserDTO.java` - 用户DTO
- `dto/WikiDTO.java` - 知识库DTO
- `dto/PageDTO.java` - 页面DTO
- `dto/CommentDTO.java` - 评论DTO
- `vo/UserVO.java` - 用户VO
- `vo/WikiVO.java` - 知识库VO
- `vo/PageVO.java` - 页面VO
- `vo/PageTreeVO.java` - 页面树形VO
- `vo/CommentVO.java` - 评论VO

#### Mapper接口
- `mapper/UserMapper.java`
- `mapper/RoleMapper.java`
- `mapper/WikiMapper.java`
- `mapper/PageMapper.java`
- `mapper/CategoryMapper.java`
- `mapper/TagMapper.java`
- `mapper/CommentMapper.java`
- `mapper/FavoriteMapper.java`
- `mapper/NotificationMapper.java`
- `mapper/AttachmentMapper.java`
- `mapper/AuditLogMapper.java`

#### Service接口
- `service/UserService.java`
- `service/RoleService.java`
- `service/WikiService.java`
- `service/PageService.java`
- `service/CategoryService.java`
- `service/TagService.java`
- `service/CommentService.java`
- `service/SearchService.java`
- `service/AttachmentService.java`
- `service/NotificationService.java`
- `service/AuditLogService.java`

#### Service实现
- `service/impl/UserServiceImpl.java`
- `service/impl/RoleServiceImpl.java`
- `service/impl/WikiServiceImpl.java`
- `service/impl/PageServiceImpl.java`
- `service/impl/CategoryServiceImpl.java`
- `service/impl/TagServiceImpl.java`
- `service/impl/CommentServiceImpl.java`
- `service/impl/SearchServiceImpl.java`
- `service/impl/AttachmentServiceImpl.java`
- `service/impl/NotificationServiceImpl.java`
- `service/impl/AuditLogServiceImpl.java`

#### Controller
- `controller/AuthController.java` - 认证接口
- `controller/UserController.java` - 用户接口
- `controller/RoleController.java` - 角色接口
- `controller/WikiController.java` - 知识库接口
- `controller/PageController.java` - 页面接口
- `controller/CategoryController.java` - 分类接口
- `controller/TagController.java` - 标签接口
- `controller/CommentController.java` - 评论接口
- `controller/SearchController.java` - 搜索接口
- `controller/AttachmentController.java` - 附件接口
- `controller/NotificationController.java` - 通知接口

#### 通用类
- `common/Result.java` - 统一响应结果
- `common/PageResult.java` - 分页结果
- `common/exception/BusinessException.java` - 业务异常
- `common/exception/GlobalExceptionHandler.java` - 全局异常处理
- `common/utils/JwtUtils.java` - JWT工具
- `common/utils/SecurityUtils.java` - 安全工具

### 6.2 前端文件

#### 项目结构
```
frontend/
├── public/
├── src/
│   ├── api/           # API接口
│   ├── assets/        # 静态资源
│   ├── components/    # 公共组件
│   ├── router/        # 路由配置
│   ├── stores/        # Pinia状态管理
│   ├── views/         # 页面视图
│   ├── App.vue
│   └── main.js
├── package.json
└── vite.config.js
```

#### 视图页面
- `views/LoginView.vue` - 登录页
- `views/RegisterView.vue` - 注册页
- `views/HomeView.vue` - 首页
- `views/WikiListView.vue` - 知识库列表
- `views/WikiDetailView.vue` - 知识库详情
- `views/PageEditView.vue` - 页面编辑
- `views/PageDetailView.vue` - 页面详情
- `views/SearchView.vue` - 搜索页
- `views/UserProfileView.vue` - 用户中心

### 6.3 配置文件
- `pom.xml` - Maven依赖
- `application.yml` - 应用配置
- `application-dev.yml` - 开发环境配置
- `schema.sql` - 数据库初始化脚本

---

## 七、预期成果

1. 完整的Spring Boot 3.5.11后端项目
2. 完整的Vue 3 + Element Plus+typescript前端项目
3. MySQL 8.0数据库脚本
4. 支持用户认证、知识库管理、页面管理、评论互动、全文搜索等核心功能
5. 完善的权限控制体系
6. 审计日志记录

---

## 八、其他要求
1. 需要加代码注释
2. 当数据库及表不存在时，系统启动创建
3. 不使用Lombok
4. 不使用Hutool
5. 代码写完后，要编译打包验证