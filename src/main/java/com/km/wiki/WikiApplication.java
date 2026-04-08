package com.km.wiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * KM/Wiki知识管理系统启动类
 * 
 * @author KM Team
 * @version 1.0
 */
@SpringBootApplication
public class WikiApplication {

    /**
     * 应用程序入口
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(WikiApplication.class, args);
    }
}
