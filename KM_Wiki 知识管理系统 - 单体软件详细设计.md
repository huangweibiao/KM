# KM/Wiki 知识管理系统 - 单体软件详细设计

## 一、系统目标与设计原则

### 🎯 目标

- 统一知识沉淀（文档、FAQ、经验、流程）

- 高效检索（全文搜索 + 语义搜索）

- 支持协作（评论、编辑、版本）

- 权限与安全控制

- 可扩展（未来拆分微服务）

### 📌 设计原则

- **单体优先（Modular Monolith）**

- **领域驱动设计（DDD）**

- **高内聚低耦合**

- **可观测性优先**

- **搜索优先（Search-first）**

## 二、系统概述

### 2.1 技术栈建议

|维度|技术选型|
|---|---|
|后端|Spring Boot 3.x + MyBatis-Plus + Spring Security|
|前端|Vue 3 + Element Plus / React + Ant Design / React + Next.js|
|数据库|MySQL 8.0 + Redis（缓存/会话）|
|全文检索|Elasticsearch（推荐）、MySQL全文索引（初期备用）|
|AI能力（增强）|Embedding：OpenAI / 本地模型；向量数据库：Milvus / pgvector|
|文件存储|S3 / OSS|
|可观测性|ELK（日志）、Prometheus + Grafana（指标）、OpenTelemetry（链路追踪）|
### 2.2 核心功能模块

- 用户/权限/角色管理（IAM）

- 知识库/空间管理

- 文档/Wiki/FAQ页面管理

- 版本管理

- 标签/分类管理

- 评论/协作讨论（@提及、点赞/收藏/关注）

- 全文搜索（关键词+语义+混合搜索）

- 审核与流程管理（审批流、敏感词检测）

- 通知管理（站内信、邮件、Webhook）

- 文件与附件管理

- 操作审计日志

- 推荐与知识图谱（可选高级）

- AI问答（RAG，进阶能力）

## 三、总体架构设计

### 3.1 架构风格

```Plain Text

前端（Web / Mobile）
        ↓
API网关（单体内）
        ↓
应用层（Application Layer）
        ↓
领域层（Domain Layer）
        ↓
基础设施层（Infrastructure）
        ↓
数据库 / 搜索引擎 / 对象存储
```

### 3.2 部署架构

```Plain Text

Nginx
  ↓
应用（单体）
  ↓
MySQL + Redis + ES
  ↓
OSS
```

### 3.3 模块化拆分（核心）

|模块|核心能力|
|---|---|
|用户与权限模块（IAM）|用户管理、角色（RBAC）、权限（资源级）、组织架构（部门）、ABAC属性增强|
|知识内容模块|文档/Wiki/FAQ管理、富文本/Markdown解析、分类、草稿/发布、状态流转|
|知识版本模块|历史版本记录、diff对比、回滚机制|
|搜索模块|全文搜索（关键词）、高级过滤、语义搜索（Embedding）、混合搜索|
|协作模块|评论、@提及、点赞/收藏、关注知识|
|推荐与知识图谱模块|热门知识、相关推荐、知识关联图谱（可选）|
|审核与流程模块|审批流（发布前）、敏感词检测、状态流转|
|通知模块|站内信、邮件通知、Webhook|
|文件与附件模块|文件上传、图片处理、对象存储（OSS/S3）|
## 四、数据库设计（完整）

### 4.1 用户与权限模块

#### 表1: `user` (用户表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|用户ID|
|username|VARCHAR(64)|NOT NULL, UNIQUE|用户名|
|email|VARCHAR(128)|NOT NULL, UNIQUE|邮箱|
|password_hash|VARCHAR(255)|NOT NULL|密码哈希|
|avatar_url|VARCHAR(512)||头像URL|
|nickname|VARCHAR(64)||昵称|
|dept_id|BIGINT||所属部门ID（组织架构）|
|status|TINYINT|NOT NULL, DEFAULT 1|状态: 0禁用,1正常|
|last_login_at|DATETIME||最后登录时间|
|created_at|DATETIME|NOT NULL, DEFAULT CURRENT_TIMESTAMP|创建时间|
|updated_at|DATETIME|NOT NULL, DEFAULT CURRENT_TIMESTAMP ON UPDATE|更新时间|
#### 表2: `role` (角色表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|角色ID|
|code|VARCHAR(64)|NOT NULL, UNIQUE|角色代码: ADMIN, EDITOR, VIEWER|
|name|VARCHAR(64)|NOT NULL|角色名称|
|description|VARCHAR(255)||描述|
|created_at|DATETIME|NOT NULL|创建时间|
#### 表3: `user_role` (用户角色关联表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|关联ID|
|user_id|BIGINT|NOT NULL, FK([user.id](user.id))|用户ID|
|role_id|BIGINT|NOT NULL, FK([role.id](role.id))|角色ID|
|created_at|DATETIME|NOT NULL|创建时间|
#### 表4: `permission` (权限表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|权限ID|
|code|VARCHAR(128)|NOT NULL, UNIQUE|权限代码: wiki:create, page:edit|
|name|VARCHAR(64)|NOT NULL|权限名称|
|resource_type|VARCHAR(32)||资源类型: WIKI, PAGE, COMMENT|
|action|VARCHAR(32)||操作: CREATE, READ, UPDATE, DELETE|
|created_at|DATETIME|NOT NULL|创建时间|
#### 表5: `role_permission` (角色权限关联表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|关联ID|
|role_id|BIGINT|NOT NULL, FK([role.id](role.id))|角色ID|
|permission_id|BIGINT|NOT NULL, FK([permission.id](permission.id))|权限ID|
|created_at|DATETIME|NOT NULL|创建时间|
#### 表6: `department` (部门表，补充)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|部门ID|
|name|VARCHAR(64)|NOT NULL|部门名称|
|parent_id|BIGINT|FK([department.id](department.id))|父部门ID（树形结构）|
|sort_order|INT|DEFAULT 0|排序|
|created_at|DATETIME|NOT NULL|创建时间|
|updated_at|DATETIME|NOT NULL|更新时间|
### 4.2 知识库/空间模块

#### 表7: `wiki` (知识库表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|知识库ID|
|name|VARCHAR(128)|NOT NULL|知识库名称|
|slug|VARCHAR(128)|NOT NULL, UNIQUE|URL标识符|
|description|TEXT||描述|
|logo_url|VARCHAR(512)||Logo URL|
|owner_id|BIGINT|NOT NULL, FK([user.id](user.id))|创建者/所有者ID|
|visibility|TINYINT|NOT NULL, DEFAULT 1|可见性: 1公开,2内部,3私有|
|is_archived|TINYINT|NOT NULL, DEFAULT 0|是否归档: 0否,1是|
|page_count|INT|DEFAULT 0|页面数量(冗余)|
|created_at|DATETIME|NOT NULL|创建时间|
|updated_at|DATETIME|NOT NULL|更新时间|
#### 表8: `wiki_member` (知识库成员表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|关联ID|
|wiki_id|BIGINT|NOT NULL, FK([wiki.id](wiki.id))|知识库ID|
|user_id|BIGINT|NOT NULL, FK([user.id](user.id))|用户ID|
|role_in_wiki|VARCHAR(32)|NOT NULL|角色: OWNER, ADMIN, EDITOR, VIEWER|
|created_at|DATETIME|NOT NULL|创建时间|
### 4.3 文档/页面模块

#### 表9: `category` (分类表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|分类ID|
|wiki_id|BIGINT|NOT NULL, FK([wiki.id](wiki.id))|所属知识库ID|
|parent_id|BIGINT|FK([category.id](category.id))|父分类ID(树形结构)|
|name|VARCHAR(64)|NOT NULL|分类名称|
|slug|VARCHAR(128)|NOT NULL|URL标识符|
|sort_order|INT|DEFAULT 0|排序顺序|
|created_at|DATETIME|NOT NULL|创建时间|
|updated_at|DATETIME|NOT NULL|更新时间|
#### 表10: `page` (页面/知识表，整合knowledge表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|页面ID|
|wiki_id|BIGINT|NOT NULL, FK([wiki.id](wiki.id))|所属知识库ID|
|category_id|BIGINT|FK([category.id](category.id))|分类ID|
|parent_page_id|BIGINT|FK([page.id](page.id))|父页面ID(嵌套页面)|
|title|VARCHAR(256)|NOT NULL|页面标题|
|slug|VARCHAR(256)|NOT NULL|URL标识符|
|content|LONGTEXT||页面内容(Markdown/HTML)|
|content_format|VARCHAR(16)|DEFAULT 'markdown'|内容格式: markdown, html|
|type|VARCHAR(16)|DEFAULT 'doc'|知识类型: doc/wiki/faq|
|version|INT|NOT NULL, DEFAULT 1|当前版本号|
|author_id|BIGINT|NOT NULL, FK([user.id](user.id))|创建者ID|
|last_editor_id|BIGINT|FK([user.id](user.id))|最后编辑者ID|
|status|TINYINT|DEFAULT 0|状态: 0草稿/1已发布|
|published_at|DATETIME||发布时间|
|view_count|INT|DEFAULT 0|浏览次数|
|like_count|INT|DEFAULT 0|点赞数|
|is_deleted|TINYINT|DEFAULT 0|逻辑删除|
|created_at|DATETIME|NOT NULL|创建时间|
|updated_at|DATETIME|NOT NULL|更新时间|
#### 表11: `page_version` (页面版本表，整合knowledge_version)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|版本ID|
|page_id|BIGINT|NOT NULL, FK([page.id](page.id))|页面ID|
|version|INT|NOT NULL|版本号|
|title|VARCHAR(256)|NOT NULL|标题快照|
|content|LONGTEXT|NOT NULL|内容快照|
|editor_id|BIGINT|NOT NULL, FK([user.id](user.id))|编辑者ID|
|change_note|VARCHAR(512)||修改备注|
|created_at|DATETIME|NOT NULL|创建时间|
#### 表12: `tag` (标签表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|标签ID|
|wiki_id|BIGINT|FK([wiki.id](wiki.id))|所属知识库ID(全局为空)|
|name|VARCHAR(64)|NOT NULL|标签名称|
|created_at|DATETIME|NOT NULL|创建时间|
#### 表13: `page_tag` (页面标签关联表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|关联ID|
|page_id|BIGINT|NOT NULL, FK([page.id](page.id))|页面ID|
|tag_id|BIGINT|NOT NULL, FK([tag.id](tag.id))|标签ID|
|created_at|DATETIME|NOT NULL|创建时间|
### 4.4 协作与互动模块

#### 表14: `comment` (评论表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|评论ID|
|page_id|BIGINT|NOT NULL, FK([page.id](page.id))|页面ID|
|user_id|BIGINT|NOT NULL, FK([user.id](user.id))|评论者ID|
|parent_id|BIGINT|FK([comment.id](comment.id))|父评论ID(支持嵌套)|
|content|TEXT|NOT NULL|评论内容|
|like_count|INT|DEFAULT 0|点赞数|
|is_deleted|TINYINT|DEFAULT 0|逻辑删除|
|created_at|DATETIME|NOT NULL|创建时间|
|updated_at|DATETIME|NOT NULL|更新时间|
#### 表15: `favorite` (收藏表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|收藏ID|
|user_id|BIGINT|NOT NULL, FK([user.id](user.id))|用户ID|
|page_id|BIGINT|NOT NULL, FK([page.id](page.id))|页面ID|
|created_at|DATETIME|NOT NULL|创建时间|
#### 表16: `watch` (关注/订阅表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|关注ID|
|user_id|BIGINT|NOT NULL, FK([user.id](user.id))|用户ID|
|page_id|BIGINT|NOT NULL, FK([page.id](page.id))|页面ID|
|watch_type|VARCHAR(16)|DEFAULT 'ALL'|类型: ALL, COMMENT, EDIT|
|created_at|DATETIME|NOT NULL|创建时间|
#### 表17: `page_reaction` (页面表情反应表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|反应ID|
|page_id|BIGINT|NOT NULL, FK([page.id](page.id))|页面ID|
|user_id|BIGINT|NOT NULL, FK([user.id](user.id))|用户ID|
|reaction_type|VARCHAR(32)|NOT NULL|类型: LIKE, HEART, THUMBS_UP|
|created_at|DATETIME|NOT NULL|创建时间|
### 4.5 搜索与审计模块

#### 表18: `search_index` (搜索索引表 - MySQL全文索引备用)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK|与page_id一致|
|page_id|BIGINT|NOT NULL, FK([page.id](page.id))|页面ID|
|title|VARCHAR(256)|NOT NULL|标题|
|content|LONGTEXT||纯文本内容|
|fulltext_index|TEXT|FULLTEXT|全文索引字段|
|vector|BLOB||语义向量（补充，适配Embedding）|
|created_at|DATETIME|NOT NULL|创建时间|
|updated_at|DATETIME|NOT NULL|更新时间|
#### 表19: `audit_log` (审计日志表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|日志ID|
|user_id|BIGINT|FK([user.id](user.id))|操作用户ID|
|username|VARCHAR(64)||用户名快照|
|action|VARCHAR(64)|NOT NULL|操作类型: CREATE, UPDATE, DELETE|
|resource_type|VARCHAR(32)|NOT NULL|资源类型: PAGE, WIKI, COMMENT|
|resource_id|BIGINT||资源ID|
|old_value|TEXT||旧值(JSON)|
|new_value|TEXT||新值(JSON)|
|ip_address|VARCHAR(45)||IP地址|
|user_agent|VARCHAR(512)||User Agent|
|created_at|DATETIME|NOT NULL|创建时间|
#### 表20: `notification` (通知表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|通知ID|
|user_id|BIGINT|NOT NULL, FK([user.id](user.id))|接收用户ID|
|type|VARCHAR(32)|NOT NULL|类型: COMMENT, MENTION, EDIT|
|title|VARCHAR(256)|NOT NULL|通知标题|
|content|TEXT||通知内容|
|link_url|VARCHAR(512)||跳转链接|
|is_read|TINYINT|DEFAULT 0|是否已读|
|notify_type|VARCHAR(16)|DEFAULT 'INNER'|通知方式: INNER(站内信)/EMAIL/WEBHOOK|
|created_at|DATETIME|NOT NULL|创建时间|
|updated_at|DATETIME|NOT NULL|更新时间|
### 4.6 文件与附件模块（补充）

#### 表21: `attachment` (附件表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|附件ID|
|page_id|BIGINT|NOT NULL, FK([page.id](page.id))|关联页面ID|
|name|VARCHAR(256)|NOT NULL|文件名|
|original_name|VARCHAR(256)|NOT NULL|原始文件名|
|file_size|BIGINT|NOT NULL|文件大小（字节）|
|file_type|VARCHAR(64)|NOT NULL|文件类型（MIME）|
|storage_path|VARCHAR(512)|NOT NULL|存储路径（OSS/S3）|
|url|VARCHAR(512)|NOT NULL|访问URL|
|uploader_id|BIGINT|NOT NULL, FK([user.id](user.id))|上传者ID|
|is_deleted|TINYINT|DEFAULT 0|逻辑删除|
|created_at|DATETIME|NOT NULL|创建时间|
### 4.7 审核流程模块（补充）

#### 表22: `approval_flow` (审批流表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|审批流ID|
|page_id|BIGINT|NOT NULL, FK([page.id](page.id))|关联页面ID|
|initiator_id|BIGINT|NOT NULL, FK([user.id](user.id))|发起人ID|
|approver_ids|VARCHAR(512)|NOT NULL|审批人ID列表（逗号分隔）|
|current_approver_id|BIGINT|FK([user.id](user.id))|当前审批人ID|
|status|TINYINT|NOT NULL|状态: 0待审批/1已通过/2已驳回|
|reject_reason|VARCHAR(512)||驳回原因|
|created_at|DATETIME|NOT NULL|创建时间|
|updated_at|DATETIME|NOT NULL|更新时间|
#### 表23: `approval_record` (审批记录表)

|字段名|类型|约束|描述|
|---|---|---|---|
|id|BIGINT|PK, AUTO_INCREMENT|记录ID|
|flow_id|BIGINT|NOT NULL, FK([approval_flow.id](approval_flow.id))|审批流ID|
|approver_id|BIGINT|NOT NULL, FK([user.id](user.id))|审批人ID|
|action|TINYINT|NOT NULL|操作: 1通过/2驳回|
|comment|VARCHAR(512)||审批意见|
|created_at|DATETIME|NOT NULL|审批时间|
## 五、核心API设计

### 5.1 通用规范

- 请求方式：RESTful风格，GET(查询)/POST(创建)/PUT(更新)/DELETE(删除)

- 认证方式：JWT + OAuth2

- 数据格式：JSON

- 分页参数：pageNum(默认1)、pageSize(默认20)

### 5.2 核心API列表

#### 5.2.1 用户与权限API

```Plain Text

GET    /api/users                - 获取用户列表
POST   /api/users                - 创建用户
GET    /api/users/{id}           - 获取用户详情
PUT    /api/users/{id}           - 更新用户
DELETE /api/users/{id}           - 禁用/删除用户
GET    /api/roles                - 获取角色列表
POST   /api/roles                - 创建角色
PUT    /api/roles/{id}/permissions - 分配角色权限
GET    /api/users/{id}/roles     - 获取用户角色
POST   /api/users/{id}/roles     - 分配用户角色
```

#### 5.2.2 知识库API

```Plain Text

GET    /api/wikis               - 获取知识库列表
POST   /api/wikis               - 创建知识库
GET    /api/wikis/{id}          - 获取知识库详情
PUT    /api/wikis/{id}          - 更新知识库
DELETE /api/wikis/{id}          - 归档/删除知识库
GET    /api/wikis/{id}/members  - 获取成员列表
POST   /api/wikis/{id}/members  - 添加成员
DELETE /api/wikis/{id}/members/{userId} - 移除成员
```

#### 5.2.3 页面API

```Plain Text

GET    /api/wikis/{wikiId}/pages         - 获取页面列表(树形)
POST   /api/wikis/{wikiId}/pages         - 创建页面
GET    /api/pages/{id}                   - 获取页面内容
PUT    /api/pages/{id}                   - 更新页面
DELETE /api/pages/{id}                   - 删除页面(软删除)
GET    /api/pages/{id}/versions          - 获取版本历史
POST   /api/pages/{id}/versions/{v}/restore - 恢复版本
POST   /api/pages/{id}/like              - 点赞/取消点赞
POST   /api/pages/{id}/submit-approval   - 提交审批
GET    /api/pages/{id}/approval-flow     - 获取审批状态
```

#### 5.2.4 标签与分类API

```Plain Text

GET    /api/wikis/{wikiId}/tags          - 获取标签列表
POST   /api/wikis/{wikiId}/tags          - 创建标签
DELETE /api/tags/{id}                    - 删除标签
POST   /api/pages/{id}/tags              - 绑定标签
DELETE /api/pages/{id}/tags/{tagId}      - 解绑标签
GET    /api/wikis/{wikiId}/categories    - 获取分类列表
POST   /api/wikis/{wikiId}/categories    - 创建分类
PUT    /api/categories/{id}              - 更新分类
DELETE /api/categories/{id}              - 删除分类
```

#### 5.2.5 协作互动API

```Plain Text

GET    /api/pages/{pageId}/comments      - 获取评论列表
POST   /api/pages/{pageId}/comments      - 发表评论
PUT    /api/comments/{id}                - 编辑评论
DELETE /api/comments/{id}                - 删除评论
POST   /api/pages/{id}/favorite          - 收藏/取消收藏
POST   /api/pages/{id}/watch             - 关注/取消关注
GET    /api/users/{id}/notifications     - 获取通知列表
PUT    /api/notifications/{id}/read      - 标记通知已读
```

#### 5.2.6 搜索API

```Plain Text

GET    /api/search?q={keyword}&wikiId={wikiId} - 关键词搜索
GET    /api/search/semantic?q={keyword}        - 语义搜索
GET    /api/search/mixed?q={keyword}           - 混合搜索
GET    /api/search/suggest?q={keyword}         - 搜索建议
```

#### 5.2.7 文件附件API

```Plain Text

POST   /api/pages/{id}/attachments       - 上传附件
GET    /api/pages/{id}/attachments       - 获取附件列表
DELETE /api/attachments/{id}             - 删除附件
GET    /api/attachments/{id}/download    - 下载附件
```

## 六、核心业务逻辑

### 6.1 页面版本管理

- 每次`UPDATE`页面操作自动创建`page_version`快照，记录标题、内容、编辑者、修改备注

- 版本号自增，保留最近100个版本（可通过配置调整），超出部分定时清理

- 支持版本diff对比（文本级）和版本恢复，恢复后生成新版本

### 6.2 权限控制矩阵

|操作|OWNER|ADMIN|EDITOR|VIEWER|
|---|---|---|---|---|
|查看页面|✅|✅|✅|✅|
|创建页面|✅|✅|✅|❌|
|编辑自己页面|✅|✅|✅|❌|
|编辑他人页面|✅|✅|❌|❌|
|删除页面|✅|✅|❌|❌|
|管理成员|✅|✅|❌|❌|
|删除知识库|✅|❌|❌|❌|
|提交页面审批|✅|✅|✅|❌|
|审批页面发布|✅|✅|❌|❌|
|上传附件|✅|✅|✅|❌|
### 6.3 搜索架构设计

#### 6.3.1 搜索流程

```Plain Text

用户搜索
   ↓
API网关
   ↓
Search Service
   ↓
关键词检索 + 语义向量检索（混合模式）
   ↓
Elasticsearch/MySQL索引
   ↓
结果重排序（标题>标签>内容）
   ↓
返回结果
```

#### 6.3.2 搜索能力

- **关键词搜索**：中文分词（IK），标题权重 > 标签权重 > 内容权重

- **语义搜索**：用户输入 → Embedding生成向量 → 向量数据库检索 → topK结果 → 重排序

- **混合搜索**：关键词+向量结果融合排序，兼顾精准度和语义相关性

### 6.4 审核流程逻辑

```Plain Text

用户编辑页面 → 提交审批 → 创建approval_flow → 通知审批人 → 审批人操作（通过/驳回）→ 
  通过：页面状态改为已发布 + 索引到ES → 驳回：通知发起人修改
```

### 6.5 性能优化策略

- **缓存层**：Redis缓存热门页面、用户权限、搜索结果、标签/分类列表

- **存储优化**：页面内容CDN静态化，大文件分片上传，附件OSS存储

- **数据库优化**：索引优化（页面标题/ID、标签关联表），分页使用search_after（ES）/limit+offset（MySQL）

- **异步处理**：通知发送、审计日志写入、ES索引更新采用异步线程池

- **查询优化**：树形结构（分类/页面）使用递归查询或预加载

## 七、部署配置示例

### 7.1 application.yml

```YAML

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/km_wiki?useUnicode=true&characterEncoding=utf8mb4&useSSL=false
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver
  redis:
    host: ${REDIS_HOST:localhost}
    port: ${REDIS_PORT:6379}
    password: ${REDIS_PASSWORD:}
    database: 0
  elasticsearch:
    uris: ${ES_URIS:http://localhost:9200}
    username: ${ES_USERNAME:}
    password: ${ES_PASSWORD:}
  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 500MB

mybatis-plus:
  mapper-locations: classpath*:mapper/**/*.xml
  type-aliases-package: com.km.wiki.entity
  global-config:
    db-config:
      logic-delete-field: is_deleted
      logic-delete-value: 1
      logic-not-delete-value: 0
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false

app:
  search:
    engine: elasticsearch  # 可选值：elasticsearch/mysql
    semantic:
      enable: true
      embedding-model: openai  # 可选值：openai/local
  version:
    max-history: 100
  oss:
    endpoint: ${OSS_ENDPOINT:}
    access-key: ${OSS_ACCESS_KEY:}
    secret-key: ${OSS_SECRET_KEY:}
    bucket-name: ${OSS_BUCKET:}
  approval:
    notify-type: EMAIL,INNER  # 审批通知方式
```

## 八、扩展性设计

### 8.1 模块化单体设计

- 每个核心模块独立包结构（[com.km.wiki.module.xxx](com.km.wiki.module.xxx)）

- 模块间通过接口交互，避免直接依赖

- 核心服务接口化，便于后续替换实现

### 8.2 微服务拆分路线图

1. **第一阶段**：拆分独立部署的搜索服务（Elasticsearch+语义检索）

2. **第二阶段**：拆分用户权限服务（IAM）

3. **第三阶段**：拆分内容服务（页面/文档/附件）

4. **第四阶段**：拆分协作服务（评论/收藏/通知）

5. **第五阶段**：拆分AI服务（Embedding/知识图谱/RAG）

## 九、安全设计

- **认证**：JWT令牌认证，支持OAuth2第三方登录

- **授权**：RBAC+ABAC混合权限模型，资源级权限控制

- **数据安全**：密码哈希存储，敏感字段脱敏，操作审计日志

- **接口安全**：防XSS、CSRF、SQL注入，接口限流，IP白名单

- **存储安全**：附件病毒扫描，文件访问权限控制

## 十、进阶能力（可选）

- AI问答（RAG）：基于知识库内容的上下文问答

- 知识热度分析：统计页面访问/编辑/点赞数据，生成热度排行

- 知识图谱：基于标签/分类/内容关联构建知识关系图谱

- 自动标签生成：AI提取页面关键词生成标签

- OCR文档解析：上传图片/PDF自动解析文本内容入库
> （注：文档部分内容可能由 AI 生成）