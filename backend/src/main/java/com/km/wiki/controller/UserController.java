package com.km.wiki.controller;

import com.km.wiki.common.Result;
import com.km.wiki.dto.UserDTO;
import com.km.wiki.service.UserService;
import com.km.wiki.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * 处理用户相关的HTTP请求
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     *
     * @param pageNum  页码，默认1
     * @param pageSize 每页大小，默认20
     * @return 用户分页列表
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Page<UserVO>> getUserList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return Result.success(userService.getUserList(pageable));
    }

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityUtils.isCurrentUser(#id)")
    public Result<UserVO> getUserById(@PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }

    /**
     * 更新用户信息
     *
     * @param id      用户ID
     * @param userDTO 用户更新信息
     * @return 更新后的用户
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityUtils.isCurrentUser(#id)")
    public Result<UserVO> updateUser(@PathVariable Long id, @RequestBody UserDTO.UpdateRequest userDTO) {
        return Result.success(userService.updateUser(id, userDTO));
    }

    /**
     * 禁用用户
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> disableUser(@PathVariable Long id) {
        userService.disableUser(id);
        return Result.success();
    }
}
