# 后端代码迁移到 backend 目录 - 设计文档

## 需求概述

将当前位于项目根目录的后端代码（Spring Boot 项目）迁移到 `backend/` 目录下，使项目结构更清晰，前后端分离。

## 当前项目结构

```
KM/
├── src/                          # 后端源代码（需要迁移）
│   └── main/
│       ├── java/com/km/wiki/     # Java 源代码
│       └── resources/            # 配置文件
├── pom.xml                       # Maven 配置（需要迁移）
├── backend/                      # 目标目录（当前为空）
├── frontend/                     # 前端代码（保持不变）
├── README.md
└── LICENSE
```

## 目标项目结构

```
KM/
├── backend/                      # 后端代码目录
│   ├── src/
│   │   └── main/
│   │       ├── java/com/km/wiki/
│   │       └── resources/
│   └── pom.xml
├── frontend/                     # 前端代码（保持不变）
├── README.md
└── LICENSE
```

## 迁移内容

### 需要迁移的文件/目录

1. **src/** 目录 - 完整的后端源代码
   - `src/main/java/` - Java 源代码
   - `src/main/resources/` - 配置文件（application.yml, application-dev.yml, schema.sql）

2. **pom.xml** - Maven 项目配置文件

### 保持不变的文件/目录

- `frontend/` - 前端代码
- `README.md` - 项目说明
- `LICENSE` - 许可证文件
- `KM_Wiki 知识管理系统 - 单体软件详细设计.md` - 设计文档

## 技术方案

1. 在 `backend/` 目录下创建相同的目录结构
2. 将 `src/` 目录完整移动到 `backend/src/`
3. 将 `pom.xml` 移动到 `backend/pom.xml`
4. 删除根目录下的 `src/` 目录

## 影响分析

- **构建命令变更**: 需要在 `backend/` 目录下执行 Maven 命令
- **IDE 配置**: 需要将 `backend/` 目录作为 Maven 项目导入
- **CI/CD**: 需要更新构建脚本中的路径

## 验证步骤

1. 确认所有文件已正确迁移
2. 确认目录结构正确
3. 验证后端代码完整性
