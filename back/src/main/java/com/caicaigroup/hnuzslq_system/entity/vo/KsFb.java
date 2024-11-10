package com.caicaigroup.hnuzslq_system.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 考生分班中文
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KsFb {

    private String xy; // 学院
    private String ksh; // 考生号
    private String zy; // 专业
    private String bj; // 班级
    private String xm; // 姓名
    private String xb; // 性别
    private String xh; // 学号
    private String kslbdm; // 考生类别代码
   // private String jg; // 籍贯
    private String kl; // 科类
    private String zjhm; // 证件号码
    private String lxdh; // 联系方式
    private String txdz; // 通讯地址
    private String csrq; // 出生日期
    private String sf; // 省份
    private String cj; // 成绩项
    private String jh; // 计划
    private byte[] kszp; // 照片

}
