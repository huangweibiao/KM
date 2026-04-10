package com.km.wiki.config;

import com.km.wiki.entity.User;
import com.km.wiki.entity.Role;
import com.km.wiki.entity.UserRole;
import com.km.wiki.repository.UserRepository;
import com.km.wiki.repository.RoleRepository;
import com.km.wiki.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 数据初始化配置
 * 应用启动时创建默认管理员用户和系统角色
 */
@Configuration
public class DataInitConfig {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                       RoleRepository roleRepository,
                                       UserRoleRepository userRoleRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {
            // 初始化系统角色
            initRoles(roleRepository);

            // 初始化管理员用户
            initAdminUser(userRepository, roleRepository, userRoleRepository, passwordEncoder);
        };
    }

    private void initRoles(RoleRepository roleRepository) {
        // 管理员角色
        if (!roleRepository.existsByCode("ADMIN")) {
            Role adminRole = new Role();
            adminRole.setCode("ADMIN");
            adminRole.setName("管理员");
            adminRole.setDescription("系统管理员，拥有所有权限");
            roleRepository.save(adminRole);
        }

        // 编辑者角色
        if (!roleRepository.existsByCode("EDITOR")) {
            Role editorRole = new Role();
            editorRole.setCode("EDITOR");
            editorRole.setName("编辑者");
            editorRole.setDescription("知识库编辑者，可以创建和编辑文档");
            roleRepository.save(editorRole);
        }

        // 查看者角色
        if (!roleRepository.existsByCode("VIEWER")) {
            Role viewerRole = new Role();
            viewerRole.setCode("VIEWER");
            viewerRole.setName("查看者");
            viewerRole.setDescription("知识库查看者，只有阅读权限");
            roleRepository.save(viewerRole);
        }
    }

    private void initAdminUser(UserRepository userRepository,
                                RoleRepository roleRepository,
                                UserRoleRepository userRoleRepository,
                                PasswordEncoder passwordEncoder) {
        // 创建默认管理员账户 (仅当不存在时)
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setNickname("系统管理员");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setStatus(1);
            User savedAdmin = userRepository.save(admin);

            // 分配管理员角色
            roleRepository.findByCode("ADMIN").ifPresent(role -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(savedAdmin.getId());
                userRole.setRoleId(role.getId());
                userRoleRepository.save(userRole);
            });
        }
    }
}
