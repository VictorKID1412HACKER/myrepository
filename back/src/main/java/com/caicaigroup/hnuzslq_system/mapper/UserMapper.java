package com.caicaigroup.hnuzslq_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caicaigroup.hnuzslq_system.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户管理 mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 删除用户
     * @param id 用户id
     * @return 1删除成功 0删除失败
     * @throws Exception
     */
    int deleteByPrimaryKey(Integer id) throws Exception;

    /**
     * 新增用户 所有数据不能为空
     * @param record 用户
     * @return 1成功 0失败
     */
    @Override
    int insert(User record);

    /**
     * 有选择地新增用户 除了主键其他数据可为空
     * @param record
     * @return 1成功 0失败
     */
    int insertSelective(User record);

    /**
     * 根据id获取一个用户
     * @param id 自增主键
     * @return 用户
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 根据userId获取一个用户
     * @param userId 学号工号
     * @return 用户
     */
    User selectByUserId(String userId)throws Exception;

    /**
     * 获取用户列表
     * @return 用户列表
     * @throws Exception 数据库操作异常
     */
    List<User> selectAllUsers()throws Exception;

    /**
     * 获取所有用户工号
     * @return 用户工号集合
     * @throws Exception
     */
    List<String> selectAllUserId()throws Exception;

    /**
     * 修改用户信息 除了id其他数据可为空
     * @param record 用户
     * @return 1 修改成功 0失败
     * @throws Exception
     */
    int updateByPrimaryKeySelective(User record)throws Exception;

    /**
     * 修改用户信息 所有数据不能为空
     * @param record 用户
     * @return 1 修改成功 0失败
     */
    int updateByPrimaryKey(User record);


}