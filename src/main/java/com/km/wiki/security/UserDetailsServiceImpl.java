package com.km.wiki.security;

import com.km.wiki.entity.User;
import com.km.wiki.repository.UserRepository;
import com.km.wiki.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户详情服务实现类
 * 实现Spring Security的UserDetailsService接口，用于加载用户信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * 根据用户名加载用户详情
     *
     * @param username 用户名
     * @return UserDetails对象
     * @throws UsernameNotFoundException 用户不存在时抛出
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return buildUserDetails(user);
    }

    /**
     * 根据用户ID加载用户详情
     *
     * @param userId 用户ID
     * @return UserDetails对象
     * @throws UsernameNotFoundException 用户不存在时抛出
     */
    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        return buildUserDetails(user);
    }

    /**
     * 构建UserDetails对象
     *
     * @param user 用户实体
     * @return CustomUserDetails对象
     */
    private CustomUserDetails buildUserDetails(User user) {
        // 获取用户角色
        List<String> roleCodes = userRoleRepository.findRoleCodesByUserId(user.getId());

        // 转换为Spring Security的权限对象
        List List<SimpleGrantedAuthority> authorities = roleCodes.stream()
                .map(roleCode -> new SimpleGrantedAuthority("ROLE_" + roleCode))
                .collect(Collectors.toList());

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getStatus() == 1, // 状态为1表示启用
                authorities
        );
    }
}
