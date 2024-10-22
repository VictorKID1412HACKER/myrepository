package com.caicaigroup.hnuzslq_system.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 21g
 * @version 1.0
 * @className KsxxUpateInfo
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KsxxUpateInfo {
    private String ksh; // 考生号
    private String jh; // 计划
    private String kl; // 科类
    private String zy; // 专业
}
