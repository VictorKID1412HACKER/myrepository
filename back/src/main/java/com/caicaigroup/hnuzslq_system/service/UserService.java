package com.caicaigroup.hnuzslq_system.service;

import com.caicaigroup.hnuzslq_system.entity.User;

import java.util.List;

/**
 * @author 21g
 * @version 1.0
 * @className UserService
 * @since 1.0
 */
public interface UserService {

    /**
     * 根据id获得用户
     * @param Id 用户id
     * @return
     */
    User getById(String Id);

    /**
     * 登录
     * @param user 用户
     * @return
     */
    User login(User user);

    /**
     * 获取当前登陆用户
     * @return user 用户
     */
    User getCurrentUser();

    /**
     * 管理员添加用户
     * @param user 用户
     * @return user
     */
    Integer addUser(User user);

    /**
     * 管理员获取用户列表
     * @return List用户集合
     */
    List<User> getUserList();

    /**
     * 管理员删除用户
     * @param id 用户id
     * @return 1成功 null失败
     */
    Integer deleteUser(Integer id);

    /**
     * 管理员修改用户信息
     * @param user 用户
     * @return 1修改成功 null修改失败
     */
    Integer updateUser(User user);

    /**
     * 根据userId获取单个用户
     * @param userId
     * @return
     */
    User getUserByUserId(String userId);
}
