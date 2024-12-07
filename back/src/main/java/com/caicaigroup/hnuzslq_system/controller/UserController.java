package com.caicaigroup.hnuzslq_system.controller;

import com.caicaigroup.hnuzslq_system.common.Constants;
import com.caicaigroup.hnuzslq_system.common.Result;
import com.caicaigroup.hnuzslq_system.entity.User;
import com.caicaigroup.hnuzslq_system.exception.ServiceException;
import com.caicaigroup.hnuzslq_system.service.UserService;
import com.caicaigroup.hnuzslq_system.utils.MysqlUtil;
import com.caicaigroup.hnuzslq_system.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param user 用户
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if (user.getUserId() == null || "".equals(user.getUserId())) {
            throw new ServiceException(Constants.CODE_400, "id不得为空");
        }
        if (user.getUserPwd() == null || "".equals(user.getUserPwd())) {
            throw new ServiceException(Constants.CODE_400, "密码不得为空");
        }
        if (!MysqlUtil.sqlZhuru(user.getUserId()) || !MysqlUtil.sqlZhuru(user.getUserPwd())) {
            throw new ServiceException(Constants.CODE_400, "非法用户名与密码");
        }
        User userCheck = userService.login(user);
        if (userCheck != null) {
            return Result.success(userCheck);
        } else {
            return Result.error(Constants.CODE_600, "邮箱或密码错误");
        }
    }

    /**
     * 管理员添加用户
     *
     * @param user 用户
     * @return Result结果集
     */
    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user) {
        Integer userCheck = userService.addUser(user);
        if (userCheck != null) {
            if(userCheck==1){
                return Result.success(userCheck);
            }else{
                return Result.error(Constants.CODE_500, "该用户工号已存在");
            }
        } else {
            return Result.error(Constants.CODE_500, "添加新用户失败");
        }
    }

    /**
     * 管理员获取用户列表
     *
     * @return Result结果集
     */
    @GetMapping("/getUserList")
    public Result getUserList() {
        List<User> userList = userService.getUserList();
        if (userList != null) {
            if (userList.size() > 0) {
                return Result.success(userList);
            } else {
                return Result.error(Constants.CODE_500, "获取数据错误");
            }
        } else {
            return Result.error(Constants.CODE_500, "获取数据失败");
        }
    }

    /**
     * 管理员删除用户
     * @param id 用户id
     * @return Result结果集
     */
    @GetMapping("/deleteUser")
    public Result deleteUser(@RequestParam Integer id){
        Integer userCheck=userService.deleteUser(id);
        if(userCheck!=null){
            return Result.success();
        }else{
            return Result.error(Constants.CODE_500, "删除用户数据失败");
        }
    }

    /**
     * 管理员更新用户信息
     * @param user
     * @return 结果集
     */
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user){
        Integer userCheck= userService.updateUser(user);
        if(userCheck!=null){
            return Result.success();
        }else{
            return Result.error(Constants.CODE_500,"修改用户数据失败");
        }
    }

    /**
     * 根据userId获取单个用户
     * @param userId
     * @return 结果集
     */
    @GetMapping("getUser")
    public Result getUser(@RequestParam String userId){
        System.out.println(userId);
        User user=userService.getUserByUserId(userId);
        if (user!=null){
            return Result.success(user);
        }else{
            return Result.error(Constants.CODE_500,"修改用户数据失败");
        }
    }

    /**
     * 测试
     * @return
     */
    @GetMapping("/test")
    public Result test() {
        return Result.success(TokenUtils.getCurrentUser());
    }
}
