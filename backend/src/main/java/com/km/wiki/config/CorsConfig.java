package com.km.wiki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 跨域配置类
 * 配置CORS规则，允许前端应用访问API
 */
@Configuration
public class CorsConfig {

    /**
     * 配置CORS过滤器
     * 允许所有来源、所有方法和所有头部信息
     *
     * @return CorsFilter实例
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许所有来源
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        // 允许所有HTTP方法
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        // 允许所有头部信息
        config.setAllowedHeaders(Arrays.asList("*"));
        // 允许携带凭证
        config.setAllowCredentials(true);
        // 预检请求缓存时间
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
