package com.caicaigroup.hnuzslq_system.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 计划类别与专业查询
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Query {
    String jh; // 计划
    String zy; // 专业
    String sf; // 省份
    String nf; // 年份
    String yx; // 院系
}
