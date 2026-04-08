package com.km.wiki.controller;

import com.km.wiki.common.Result;
import com.km.wiki.dto.UserDTO;
import com.km.wiki.entity.User;
import com.km.wiki.security.JwtTokenProvider;
import com.km.wiki.service.UserService;
import com.km.wiki.vo.UserVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 处理用户登录、注册、登出等认证相关请求
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param loginRequest 登录请求（用户名/邮箱 + 密码）
     * @return JWT令牌和用户信息
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody UserDTO.LoginRequest loginRequest) {
        // 认证用户
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 获取用户信息
        User user = userService.findByUsername(loginRequest.getUsername());
        if (user == null) {
            user = userService.findByEmail(loginRequest.getUsername());
        }

        // 更新最后登录时间
        userService.updateLastLoginTime(user.getId());

        // 生成JWT令牌
        String jwt = jwtTokenProvider.generateToken(authentication);

        // 构建响应
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("tokenType", "Bearer");
        response.put("user", convertToVO(user));

        return Result.success(response);
    }

    /**
     * 用户注册
     *
     * @param registerRequest 注册请求
     * @return 注册成功的用户信息
     */
    @PostMapping("/register")
    public Result<UserVO> register(@Valid @RequestBody UserDTO.RegisterRequest registerRequest) {
        User user = userService.register(registerRequest);
        return Result.success(convertToVO(user));
    }

    /**
     * 用户登出
     *
     * @return 登出成功消息
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        SecurityContextHolder.clearContext();
        return Result.success("登出成功");
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前用户信息
     */
    @GetMapping("/me")
    public Result<UserVO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.success(convertToVO(user));
    }

    /**
     * 将用户实体转换为视图对象
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
