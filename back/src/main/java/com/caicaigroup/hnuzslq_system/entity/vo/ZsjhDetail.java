package com.caicaigroup.hnuzslq_system.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 招生计划中文
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZsjhDetail {
    private Integer tZsjhId; // 招生计划Id
    private String xy; // 学院
    private String zydh; // 专业代号
    private String zymc; // 专业名称
    private String kl; // 科类
    private String kldm; // 科类代码
    private int zyjhrs; // 专业计划人数
    private String sfjhrs; // 省份计划人数
    private String nf; // 年份
    private String sfdm; // 省份代码
    private String jhlbdm; // 计划类别代码
}
