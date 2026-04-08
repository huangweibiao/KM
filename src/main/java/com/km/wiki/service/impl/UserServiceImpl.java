package com.km.wiki.service.impl;

import com.km.wiki.dto.UserDTO;
import com.km.wiki.entity.User;
import com.km.wiki.entity.UserRole;
import com.km.wiki.repository.UserRepository;
import com.km.wiki.repository.UserRoleRepository;
import com.km.wiki.service.UserService;
import com.km.wiki.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 * 实现用户相关的业务逻辑
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    /**
     * 根据邮箱查找用户
     *
     * @param email 邮箱
     * @return 用户实体
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * 用户注册
     *
     * @param userDTO 用户注册信息
     * @return 注册成功的用户
     */
    @Override
    @Transactional
    public User register(UserDTO.RegisterRequest userDTO) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("邮箱已存在");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        user.setNickname(userDTO.getNickname());
        user.setStatus(1);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        // 分配默认角色(VIEWER)
        UserRole userRole = new UserRole();
        userRole.setUserId(savedUser.getId());
        userRole.setRoleId(4L); // VIEWER角色ID
        userRole.setCreatedAt(LocalDateTime.now());
        userRoleRepository.save(userRole);

        return savedUser;
    }

    /**
     * 更新用户最后登录时间
     *
     * @param userId 用户ID
     */
    @Override
    @Transactional
    public void updateLastLoginTime(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return 用户视图对象
     */
    @Override
    public UserVO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        return convertToVO(user);
    }

    /**
     * 获取用户列表
     *
     * @param pageable 分页参数
     * @return 用户分页列表
     */
    @Override
    public Page<UserVO> getUserList(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::convertToVO);
    }

    /**
     * 更新用户信息
     *
     * @param id      用户ID
     * @param userDTO 用户更新信息
     * @return 更新后的用户
     */
    @Override
    @Transactional
    public UserVO updateUser(Long id, UserDTO.UpdateRequest userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (userDTO.getNickname() != null) {
            user.setNickname(userDTO.getNickname());
        }
        if (userDTO.getAvatarUrl() != null) {
            user.setAvatarUrl(userDTO.getAvatarUrl());
        }
        if (userDTO.getDeptId() != null) {
            user.setDeptId(userDTO.getDeptId());
        }

        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);
        return convertToVO(updatedUser);
    }

    /**
     * 禁用用户
     *
     * @param id 用户ID
     */
    @Override
    @Transactional
    public void disableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        user.setStatus(0);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * 获取用户的角色代码列表
     *
     * @param userId 用户ID
     * @return 角色代码列表
     */
    @Override
    public List<String> getUserRoles(Long userId) {
        return userRoleRepository.findRoleCodesByUserId(userId);
    }

    /**
     * 将用户实体转换为视图对象
     *
     * @param user 用户实体
     * @return 用户视图对象
     */
    private UserVO convertToVO(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setEmail(user.getEmail());
        vo.setNickname(user.getNickname());
        vo.setAvatarUrl(user.getAvatarUrl());
        vo.setDeptId(user.getDeptId());
        vo.setStatus(user.getStatus());
        vo.setLastLoginAt(user.getLastLoginAt());
        vo.setCreatedAt(user.getCreatedAt());
        return vo;
    }
}
