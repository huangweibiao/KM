# Knowledge Management System

基于 SpringBoot 3.5.11 和 MySQL 8 的知识管理系统，支持 OAuth2 单点登录。

## 技术栈

- Java 21
- Spring Boot 3.5.11
- Spring Security 6.x
- Spring OAuth2 Client
- MySQL 8.0.33
- Spring Data JPA
- Maven

## 项目结构

```
km/
├── src/
│   ├── main/
│   │   ├── java/com/example/km/
│   │   │   ├── KmApplication.java           # 主应用程序
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java      # 安全配置
│   │   │   ├── controller/
│   │   │   │   ├── HealthController.java    # 健康检查
│   │   │   │   └── UserController.java      # 用户控制器
│   │   │   ├── entity/
│   │   │   │   └── User.java                # 用户实体
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java      # 用户仓库
│   │   │   └── security/
│   │   │       ├── CustomOAuth2User.java    # OAuth2 用户
│   │   │       └── CustomOAuth2UserService.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html               # 首页
│   │       │   └── login.html               # 登录页
│   │       └── application.yml              # 配置文件
│   └── test/
├── pom.xml                                  # Maven 配置
└── README.md
```

## 快速开始

### 1. 配置数据库

确保 MySQL 8 已安装并运行，默认配置：
- 数据库名：km_db
- 用户名：root
- 密码：root

如需修改，请编辑 `src/main/resources/application.yml` 文件：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database
    username: your_username
    password: your_password
```

### 2. 配置 OAuth2

#### GitHub OAuth2 配置

1. 访问 GitHub Settings -> Developer settings -> OAuth Apps -> New OAuth App
2. 填写应用信息：
   - Application name: Knowledge Management System
   - Homepage URL: http://localhost:8080
   - Authorization callback URL: http://localhost:8080/login/oauth2/code/github
3. 获取 Client ID 和 Client Secret

#### Google OAuth2 配置

1. 访问 Google Cloud Console -> APIs & Services -> Credentials
2. 创建 OAuth 2.0 客户端 ID
3. 配置授权重定向 URI: http://localhost:8080/login/oauth2/code/google
4. 获取 Client ID 和 Client Secret

#### 设置环境变量

```bash
# GitHub OAuth2
export GITHUB_CLIENT_ID=your_github_client_id
export GITHUB_CLIENT_SECRET=your_github_client_secret

# Google OAuth2
export GOOGLE_CLIENT_ID=your_google_client_id
export GOOGLE_CLIENT_SECRET=your_google_client_secret
```

或者直接修改 `application.yml` 中的配置：
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: your_github_client_id
            client-secret: your_github_client_secret
          google:
            client-id: your_google_client_id
            client-secret: your_google_client_secret
```

### 3. 构建项目

```bash
mvn clean package
```

### 4. 运行项目

```bash
mvn spring-boot:run
```

或直接运行打包后的 JAR：
```bash
java -jar target/km-1.0.0.jar
```

### 5. 访问应用

启动成功后，访问以下端点：

- 首页：http://localhost:8080/
- 登录页面：http://localhost:8080/login
- 健康检查：http://localhost:8080/api/health
- 当前用户：http://localhost:8080/api/user/me

## OAuth2 登录流程

1. 用户访问需要认证的页面
2. 自动重定向到登录页面 `/login`
3. 用户选择 OAuth2 提供商（GitHub 或 Google）
4. 重定向到 OAuth2 提供商进行授权
5. 授权成功后回调到应用
6. `CustomOAuth2UserService` 处理用户信息并保存到数据库
7. 重定向到首页

## API 端点

| 端点 | 方法 | 描述 | 认证 |
|------|------|------|------|
| `/api/health` | GET | 健康检查 | 否 |
| `/api/welcome` | GET | 欢迎信息 | 否 |
| `/api/user/me` | GET | 获取当前用户信息 | 是 |
| `/api/user/profile` | GET | 获取用户详细资料 | 是 |

## 开发说明

### 添加新的 OAuth2 提供商

1. 在 `application.yml` 中添加新的 registration 和 provider 配置
2. 在 `CustomOAuth2UserService` 中添加新提供商的用户信息解析逻辑

### 添加新功能

1. 创建实体类（Entity）在 `entity/` 包下
2. 创建仓库接口（Repository）在 `repository/` 包下
3. 创建服务类（Service）在 `service/` 包下
4. 创建控制器（Controller）在 `controller/` 包下

### 运行测试

```bash
mvn test
```

### 清理项目

```bash
mvn clean
```

## 注意事项

- SpringBoot 3.x 需要 Java 17 或更高版本
- 确保已安装 Maven 3.6+
- MySQL 8.x 连接器使用 `com.mysql.cj.jdbc.Driver`
- 生产环境请修改 OAuth2 的回调地址为实际域名
- 生产环境请启用 CSRF 保护

## 安全配置

项目使用 Spring Security 6.x，默认配置如下：
- `/`, `/login`, `/oauth2/**` 无需认证
- `/api/**` 需要认证
- 支持 CORS 配置（允许 localhost:3000 和 localhost:8080）
- 登出后清除 Session 和 Cookies

## License

MIT
>>>>>>> 2ef40aa (Initial commit: SpringBoot 3.5.11 project with OAuth2 SSO)
