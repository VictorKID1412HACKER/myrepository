package com.caicaigroup.hnuzslq_system.entity.vo;

import lombok.Data;

/**
 * 分班配置中文
 */
@Data
public class FbpzDetail {
    int id; // id编号
    String xy; // 院系
    String zy; // 专业
    int bjsl; // 班级数量
    String bjdmqz; // 班级代码前缀
    String bjmcqz; // 班级名称前缀

    public FbpzDetail() {
    }

    public FbpzDetail(int id, String xy, String zy, int bjsl, String bjdmqz, String bjmcqz) {
        this.id = id;
        this.xy = xy;
        this.zy = zy;
        this.bjsl = bjsl;
        this.bjdmqz = bjdmqz;
        this.bjmcqz = bjmcqz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getBjsl() {
        return bjsl;
    }

    public void setBjsl(int bjsl) {
        this.bjsl = bjsl;
    }

    public String getBjdmqz() {
        return bjdmqz;
    }

    public void setBjdmqz(String bjdmqz) {
        this.bjdmqz = bjdmqz;
    }

    public String getBjmcqz() {
        return bjmcqz;
    }

    public void setBjmcqz(String bjmcqz) {
        this.bjmcqz = bjmcqz;
    }
}
