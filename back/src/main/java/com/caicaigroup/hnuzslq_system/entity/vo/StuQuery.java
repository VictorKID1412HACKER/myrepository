package com.caicaigroup.hnuzslq_system.entity.vo;

import lombok.Data;

/**
 * 学生信息查询
 */
@Data
public class StuQuery {
    String jh; // 计划类别
    String zy; // 专业（支持模糊）
    String zjhm; // 证件号码
    String sf; // 省份
    String xm; // 姓名 （支持模糊）
    String nf; // 年份
    String ksh; // 考生号
    String bj; // 班级
    String yx; // 院系

    public StuQuery() {
    }

    public StuQuery(String jh, String zy, String zjhm, String sf, String xm) {
        this.jh = jh;
        this.zy = zy;
        this.zjhm = zjhm;
        this.sf = sf;
        this.xm = xm;
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }
}
