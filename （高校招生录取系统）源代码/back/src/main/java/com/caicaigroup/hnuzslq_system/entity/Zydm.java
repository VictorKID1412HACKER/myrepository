package com.caicaigroup.hnuzslq_system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@TableName("t_zydm")
public class Zydm {
    private Integer tZydmId; //自增id
    private String zydh; // 专业代号
    private String zymc; // 专业名称
    private String zydm; // 专业代码
    private String yxdm; // 院系代码
    private Date year; // 年份
}