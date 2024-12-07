package com.caicaigroup.hnuzslq_system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 高考成绩项
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@TableName("t_gkcjx")
public class Gkcjx {
    private String ksh; // 考生号
    // 高考成绩项
    private Long gkcjx01; // 语文
    private Long gkcjx02; // 数学
    private Long gkcjx03; // 英语
    private Long gkcjx04; // 物理
    private Long gkcjx05; // 化学
    private Long gkcjx06; // 生物
    private Long gkcjx07; // 政治
    private Long gkcjx08; // 历史
    private Long gkcjx09; // 地理
    private Long gkcjx10; // 综合
    private Long gkcjxByzc; // 播音主持
    private Long gkcjxBd; // 播导

}