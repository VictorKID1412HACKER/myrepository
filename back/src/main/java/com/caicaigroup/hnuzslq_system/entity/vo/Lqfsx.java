package com.caicaigroup.hnuzslq_system.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 21g
 * @version 1.0
 * @className lqfsx
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Lqfsx {
    private String zymc; // 专业名称
    private String jhlb; // 计划类别
    private String kl; // 科类
    private int num; // 专业人数
    private int maxS; // 最高分
    private int minS; // 最低分
    private int avgS; // 平均分
}
