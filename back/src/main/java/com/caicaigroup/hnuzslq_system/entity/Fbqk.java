package com.caicaigroup.hnuzslq_system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分班情况
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_fbqk")
public class Fbqk {

    private String zydh; // 专业代号
    private String yxdm; // 院系代码
    private String bjmc; // 班级名称
    private String bjdh; // 班级代号
    private Integer xssl; // 学生数量
    private Integer mnum; // 男生人数
    private Integer fnum; // 女生人数

    @TableField(exist = false)
    private List<String> kshList;

    @TableField(exist = false)
    private String bjdmqz;
}