package com.caicaigroup.hnuzslq_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 招生计划
 */
@TableName("t_zsjh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Zsjh {

    @TableId(value = "t_zsjh_id", type = IdType.AUTO)
    private Integer tZsjhId; // 自增id

    private String zydh; //专业代号
    private Integer kldm; // 科类代码
    private Integer zyjhrs; // 专业计划人数
    private String sfdm; // 省份代码
    private Integer sfjhrs; // 省份计划人数
    private String jhlbdm; // 计划类别代码
    private String year; // 年份

}