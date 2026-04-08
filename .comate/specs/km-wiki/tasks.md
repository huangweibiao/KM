# KM/Wiki 知识管理系统 - 任务清单

## 任务1: 项目基础架构搭建
- [ ] 1.1 创建 Maven 项目结构和 pom.xml
- [ ] 1.2 创建 application.yml 配置文件
- [ ] 1.3 创建 Spring Boot 启动类
- [ ] 1.4 创建通用响应结果类 Result 和 PageResult
- [ ] 1.5 创建业务异常类和全局异常处理

## 任务2: 数据库实体类创建
- [ ] 2.1 创建用户与权限相关实体 (User, Role, UserRole, Permission, RolePermission, Department)
- [ ] 2.2 创建知识库相关实体 (Wiki, WikiMember)
- [ ] 2.3 创建页面相关实体 (Category, Page, PageVersion)
- [ ] 2.4 创建标签相关实体 (Tag, PageTag)
- [ ] 2.5 创建协作互动实体 (Comment, Favorite, Watch)
- [ ] 2.6 创建系统功能实体 (AuditLog, Notification, Attachment)

## 任务3: 数据访问层实现
- [ ] 3.1 创建 Repository 接口 (使用 Spring Data JPA)
- [ ] 3.2 创建 MyBatis-Plus Mapper 接口和 XML 映射文件
- [ ] 3.3 配置数据库连接和 JPA 属性

## 任务4: 安全认证模块实现
- [ ] 4.1 创建 JWT 工具类和 Token 提供者
- [ ] 4.2 创建 JWT 认证过滤器
- [ ] 4.3 创建用户详情服务和自定义 UserDetails
- [ ] 4.4 配置 Spring Security (SecurityConfig)
- [ ] 4.5 实现认证 Controller (登录、注册、登出)

## 任务5: 用户与权限服务实现
- [ ] 5.1 实现 UserService (用户增删改查)
- [ ] 5.2 实现 RoleService (角色管理)
- [ ] 5.3 实现 UserController
- [ ] 5.4 实现 RoleController

## 任务6: 知识库模块实现
- [ ] 6.1 实现 WikiService (知识库 CRUD、成员管理)
- [ ] 6.2 实现 WikiController
- [ ] 6.3 实现知识库权限检查逻辑

## 任务7: 页面管理模块实现
- [ ] 7.1 实现 PageService (页面 CRUD、版本管理)
- [ ] 7.2 实现 PageController
- [ ] 7.3 实现版本历史查询和恢复功能
- [ ] 7.4 实现页面树形结构查询

## 任务8: 分类与标签模块实现
- [ ] 8.1 实现 CategoryService (分类 CRUD)
- [ ] 8.2 实现 CategoryController
- [ ] 8.3 实现 TagService (标签 CRUD、页面标签绑定)
- [ ] 8.4 实现 TagController

## 任务9: 评论与互动模块实现
- [ ] 9.1 实现 CommentService (评论 CRUD、嵌套查询)
- [ ] 9.2 实现 CommentController
- [ ] 9.3 实现 FavoriteService (收藏功能)
- [ ] 9.4 实现 WatchService (关注功能)

## 任务10: 搜索模块实现
- [ ] 10.1 实现 SearchService (MySQL 全文搜索)
- [ ] 10.2 实现 SearchController
- [ ] 10.3 实现搜索建议功能

## 任务11: 附件管理模块实现
- [ ] 11.1 实现 AttachmentService (文件上传、下载、删除)
- [ ] 11.2 实现 AttachmentController
- [ ] 11.3 配置文件存储路径和大小限制

## 任务12: 通知模块实现
- [ ] 12.1 实现 NotificationService (通知创建、查询、标记已读)
- [ ] 12.2 实现 NotificationController
- [ ] 12.3 集成通知触发点 (评论、收藏等)

## 任务13: 审计日志模块实现
- [ ] 13.1 实现 AuditLogService (日志记录、查询)
- [ ] 13.2 创建审计日志注解和 AOP 切面
- [ ] 13.3 在关键操作处添加审计日志

## 任务14: 前端项目搭建
- [ ] 14.1 创建 Vue 3 + TypeScript 项目结构
- [ ] 14.2 配置 Vite、TypeScript、Element Plus
- [ ] 14.3 创建路由配置和导航守卫
- [ ] 14.4 创建 Pinia Store (用户状态、应用状态)
- [ ] 14.5 创建 API 请求封装 (Axios)

## 任务15: 前端页面开发 - 认证模块
- [ ] 15.1 开发登录页面 (LoginView.vue)
- [ ] 15.2 开发注册页面 (RegisterView.vue)
- [ ] 15.3 实现登录状态持久化

## 任务16: 前端页面开发 - 首页和知识库
- [ ] 16.1 开发首页布局 (HomeView.vue)
- [ ] 16.2 开发知识库列表页面 (WikiListView.vue)
- [ ] 16.3 开发知识库详情页面 (WikiDetailView.vue)

## 任务17: 前端页面开发 - 页面管理
- [ ] 17.1 开发页面编辑器 (PageEditView.vue)
- [ ] 17.2 开发页面详情查看 (PageDetailView.vue)
- [ ] 17.3 开发页面树形导航组件
- [ ] 17.4 开发版本历史查看组件

## 任务18: 前端页面开发 - 搜索和互动
- [ ] 18.1 开发搜索页面 (SearchView.vue)
- [ ] 18.2 开发搜索结果展示组件
- [ ] 18.3 开发评论组件
- [ ] 18.4 开发收藏/关注功能按钮

## 任务19: 前端页面开发 - 用户中心
- [ ] 19.1 开发用户中心页面 (UserProfileView.vue)
- [ ] 19.2 开发通知列表组件
- [ ] 19.3 开发我的收藏页面

## 任务20: 数据库脚本和部署配置
- [ ] 20.1 编写 schema.sql 数据库初始化脚本
- [ ] 20.2 编写 data.sql 初始数据脚本
- [ ] 20.3 配置应用启动时自动创建数据库表
- [ ] 20.4 编写 README.md 项目说明文档

## 任务21: 编译打包验证
- [ ] 21.1 后端 Maven 编译打包验证
- [ ] 21.2 前端构建打包验证
- [ ] 21.3 整合测试验证
