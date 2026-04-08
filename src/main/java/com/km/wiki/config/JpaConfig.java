package com.km.wiki.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA配置类
 * 启用JPA审计功能和仓库扫描
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.km.wiki.repository")
@EnableJpaAuditing
public class JpaConfig {
    // JPA配置由Spring Boot自动配置完成
    // 此处仅启用审计功能和仓库扫描
}
