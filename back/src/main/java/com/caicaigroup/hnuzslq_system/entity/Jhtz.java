package com.caicaigroup.hnuzslq_system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 计划调整
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@TableName("t_jhtz")
public class Jhtz {
    private String userId; // 用户id
    private String zydh; // 专业代号
    private Date czsj; // 操作时间
    private String sfmc; // 省份名称
    private Integer zjrs; //增减人数
}