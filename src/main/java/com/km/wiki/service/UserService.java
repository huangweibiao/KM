package com.km.wiki.service;

import com.km.wiki.dto.UserDTO;
import com.km.wiki.entity.User;
import com.km.wiki.vo.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户服务接口
 * 定义用户相关的业务逻辑操作
 */
public interface UserService {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    User findByUsername(String username);

    /**
     * 根据ID查找用户
     *
     * @param id 用户ID
     * @return 用户实体
     */
    User findById(Long id);

    /**
     * 创建新用户
     *
     * @param userDTO 用户数据传输对象
     * @return 创建后的用户实体
     */
    User createUser(UserDTO userDTO);

    /**
     * 更新用户信息
     *
     * @param id      用户ID
     * @param userDTO 用户数据传输对象
     * @return 更新后的用户实体
     */
    User updateUser(Long id, UserDTO userDTO);

    /**
     * 删除用户（逻辑删除）
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 获取用户列表（分页）
     *
     * @param pageable 分页参数
     * @return 用户分页数据
     */
    Page<UserVO> getUserList(Pageable pageable);

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return 用户视图对象
     */
    UserVO getUserDetail(Long id);

    /**
     * 获取当前登录用户
     *
     * @return 当前用户实体
     */
    User getCurrentUser();

    /**
     * 分配用户角色
     *
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     */
    void assignRoles(Long userId, List<Long> roleIds);

    /**
     * 获取用户的角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> getUserRoleIds(Long userId);
}
