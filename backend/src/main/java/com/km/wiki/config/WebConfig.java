package com.km.wiki.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Web配置类
 * 配置静态资源处理和Web相关设置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.path:uploads}")
    private String uploadPath;

    /**
     * 配置静态资源处理器
     * 将上传目录映射为可访问的URL路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置上传文件访问路径
        Path uploadDir = Paths.get(uploadPath).toAbsolutePath().normalize();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir.toString() + "/");
        
        // 配置静态资源
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600);
    }

    /**
     * 配置视图控制器，支持 SPA (单页应用) 路由
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 所有非 API 请求都返回 index.html (支持 Vue Router 的 history 模式)
        registry.addViewController("/{path:[^\\.]*}")
                .setViewName("forward:/index.html");
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}
