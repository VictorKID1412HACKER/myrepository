package com.caicaigroup.hnuzslq_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@TableName("t_user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id; // 自增id
    private String userId; // 学号 工号
    private String userName; // 用户姓名
    private String userPwd; // 用户密码
    private Integer userRole; // 用户角色

    @TableField(exist = false)
    private String token;


}