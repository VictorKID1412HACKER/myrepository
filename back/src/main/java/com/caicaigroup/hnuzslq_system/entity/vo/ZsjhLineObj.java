package com.caicaigroup.hnuzslq_system.entity.vo;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 21g
 * @version 1.0
 * @className ZsjhLineObj
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZsjhLineObj {
    private String xy; // 学院
    private String zydh; // 专业代号
    private String zymc; // 专业名称
    private String kl; // 科类
    private String kldm; // 科类代码
    private int zyjhrs; // 专业计划人数
    private String nf; // 年份
    private String jhlbdm; // 计划类别代码
    private Map<String, String> sfdmAndsfjhrs = new HashMap<>(); // 省份代码与省份计划人数
}
