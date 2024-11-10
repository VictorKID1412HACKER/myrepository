package com.caicaigroup.hnuzslq_system.entity.vo;


/**
 * 学生名单
 */
public class Xsmd {
    private String xm; // 姓名
    private String xb; // 性别
    private String ksh; // 考生号
    private String xy; // 学院
    private String zy; // 专业

    public Xsmd() {
    }

    public Xsmd(String xm, String xb, String ksh, String xy, String zy) {
        this.xm = xm;
        this.xb = xb;
        this.ksh = ksh;
        this.xy = xy;
        this.zy = zy;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getKsh() {
        return ksh;
    }

    public void setKsh(String ksh) {
        this.ksh = ksh;
    }

    public String getXy() {
        return xy;
    }

    public void setXy(String xy) {
        this.xy = xy;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }
}
