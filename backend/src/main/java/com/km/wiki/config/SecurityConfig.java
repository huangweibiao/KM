package com.km.wiki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

/**
 * Spring Security 安全配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .authorizeHttpRequests(auth -> auth
                // 放行所有静态资源
                .requestMatchers("/assets/**", "/favicon.ico", "/*.svg", "/*.png", "/*.jpg", "/*.jpeg", "/*.gif", "/*.ico", "/*.woff", "/*.woff2", "/*.ttf").permitAll()
                // 放行前端页面
                .requestMatchers("/", "/index.html", "/login", "/register").permitAll()
                // 放行认证相关接口
                .requestMatchers("/auth/**").permitAll()
                // 放行 Swagger/API 文档
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // 放行上传文件
                .requestMatchers("/uploads/**").permitAll()
                // 其他请求需要认证
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
