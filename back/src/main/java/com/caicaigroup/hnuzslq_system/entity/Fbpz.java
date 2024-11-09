package com.caicaigroup.hnuzslq_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 分班配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@TableName("t_fbpz")
public class Fbpz {

    @TableId(value = "t_fbpz_id", type = IdType.AUTO)
    private Integer tFbpzId; // 自增id
    private String zydh; //专 业代号
    private String yxdm; // 院系代码
    private Integer bjsl; // 班级人数
    private String bjdmqz; // 班级代码前缀
    private String bjmcqz; // 班级名称前缀

}