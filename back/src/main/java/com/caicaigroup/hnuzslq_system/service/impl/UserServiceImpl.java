package com.caicaigroup.hnuzslq_system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caicaigroup.hnuzslq_system.common.Constants;
import com.caicaigroup.hnuzslq_system.entity.User;
import com.caicaigroup.hnuzslq_system.exception.ServiceException;
import com.caicaigroup.hnuzslq_system.mapper.UserMapper;
import com.caicaigroup.hnuzslq_system.service.UserService;
import com.caicaigroup.hnuzslq_system.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(String Id) {
        return userMapper.selectByPrimaryKey(Integer.parseInt(Id));
    }

    private User getUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getUserId());
        queryWrapper.eq("user_pwd", user.getUserPwd());

        User one=null;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return one;
    }

    @Override
    public User login(User user) {
        User one = getUser(user);
        if (one != null) {
            BeanUtil.copyProperties(one, user, true);
            // 设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getUserPwd());
            user.setToken(token);
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
        return user;
    }

    @Override
    public User getCurrentUser() {
        User user=null;
        try {
            user=TokenUtils.getCurrentUser();
        } catch (Exception e) {
            e.printStackTrace();
            return user;
        }
        return user;
    }


    @Override
    public Integer addUser(User user) {
        Integer flag=null;
        List<String> userIdList=null;
        String userId=user.getUserId();
        try {
            userIdList=userMapper.selectAllUserId();
            if(userIdList.contains(userId)){ // 存在该工号
                flag=-1;
            }else{
                flag=userMapper.insert(user);
                System.out.println("成功添加用户,id:"+user.getId());
            }
            //return userMapper.selectByPrimaryKey(user.getId()); 根据主键id返回该用户
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        }
        return flag;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList=null;
        try {
            userList = userMapper.selectAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return userList;
        }
        return userList;
    }

    @Override
    public Integer deleteUser(Integer id) {
        Integer isDelete=null;
        try {
            isDelete=userMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            return isDelete;
        }
        return isDelete;
    }

    @Override
    public Integer updateUser(User user) {
        Integer updateUser=null;
        try {
            if(user.getId()==null){
                return updateUser;
            }else{
                updateUser=userMapper.updateByPrimaryKeySelective(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return updateUser;
        }
        return updateUser;
    }

    @Override
    public User getUserByUserId(String userId) {
        User user =null;
        try {
            user= userMapper.selectByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return user;
        }
        return user;
    }


}
