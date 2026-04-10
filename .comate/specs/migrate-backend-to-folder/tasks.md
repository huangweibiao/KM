# 后端代码迁移到 backend 目录任务计划

- [x] Task 1: 创建 backend 目录结构
    - 1.1: 创建 backend/src 目录
    - 1.2: 创建 backend/pom.xml

- [x] Task 2: 迁移 Java 源代码
    - 2.1: 移动 src/main/java 到 backend/src/main/java
    - 2.2: 移动 src/main/resources 到 backend/src/main/resources

- [x] Task 3: 迁移 Maven 配置
    - 3.1: 复制 pom.xml 到 backend/pom.xml
    - 3.2: 更新根目录 pom.xml 为聚合模块配置

- [x] Task 4: 清理原位置文件
    - 4.1: 删除根目录 src/ 文件夹
    - 4.2: 验证迁移完整性
