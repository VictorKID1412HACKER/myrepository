package com.caicaigroup.hnuzslq_system.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 考生信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@TableName("t_ksxx")
public class Ksxx {

    @Excel(name = "考生号")
    private String ksh;

    @Excel(name = "准考证号")
    private String zkzh;

    @Excel(name = "姓名")
    private String xm;

    @Excel(name = "性别")
    private String xbdm;

    @Excel(name = "出生日期")
    private Date csrq;

    @Excel(name = "政治面貌")
    private String zzmmdm;

    @Excel(name = "民族")
    private String mzdm;

    @Excel(name = "考生类型")
    private String kslxdm;

    @Excel(name = "考生类别")
    private String kslbdm;

    @Excel(name = "毕业类别")
    private String bylbdm;

    @Excel(name = "毕业学校代码")
    private String byxxdm;

    @Excel(name = "毕业学校名称")
    private String byxxmc;

    @Excel(name = "应试卷种")
    private String ysjzdm;

    @Excel(name = "外语语种")
    private String wyyzdm;

    @Excel(name = "考生特征")
    private String kstz;

    @Excel(name = "报名单位代码")
    private String bmdwdm;

    @Excel(name = "地区")
    private String dqdm;

    @Excel(name = "证件类型")
    private String zjlxdm;

    @Excel(name = "证件号码")
    private String zjhm;

    @Excel(name = "学籍号")
    private String xjh;

    @Excel(name = "外语口试")
    private Integer wyks;

    @Excel(name = "外语听力")
    private String wytl;

    @Excel(name = "通讯地址")
    private String txdz;

    @Excel(name = "邮政编码")
    private String yzbm;

    @Excel(name = "联系手机")
    private String lxsj;

    @Excel(name = "联系电话")
    private String lxdh;

    @Excel(name = "收件人")
    private String sjr;

    @Excel(name = "考生特长")
    private String kstc;

    @Excel(name = "考生奖励和处分")
    private String ksjlhcf;

    @Excel(name = "升学评定考核意见")
    private String sxpdkhyj;

    @Excel(name = "户籍类别")
    private String hjlbdm;

    @Excel(name = "户籍代码")
    private String hjdm;

    @Excel(name = "城镇类别代码")
    private String czlbdm;

    @Excel(name = "选考科目")
    private String xkkm;

    @Excel(name = "投档单位代码")
    private String tddwdm;

    @Excel(name = "批次")
    private String pcdm;

    @Excel(name = "科类")
    private String kldm;

    @Excel(name = "计划性质代码")
    private Integer jhxzdm;

    @Excel(name = "计划类别")
    private String jhlbdm;

    @Excel(name = "投档类型代码")
    private Integer tdlxdm;

    @Excel(name = "成绩")
    private Long cj;

    @Excel(name = "特征成绩")
    private Long tzcj;

    @Excel(name = "投档成绩")
    private Long tdcj;

    @Excel(name = "照顾分")
    private Long zgf;

    @Excel(name = "录取专业")
    private String lqzydm;

    @Excel(name = "录取时间")
    private Date lqsj;

    @Excel(name = "录取类型代码")
    private String lqlxdm;

    @Excel(name = "退档原因代码")
    private String tdyydm;

    @Excel(name = "专业合格")
    private Boolean zyhg;

    @Excel(name = "体检合格")
    private Boolean tjhg;

    @Excel(name = "选考科目合格")
    private Boolean xkkmhg;

    @Excel(name = "录取序号")
    private Long lqxh;

    @Excel(name = "投档单编号")
    private Long tddbh;

    @Excel(name = "班级代号")
    private String bjdh;

    @Excel(name = "学号")
    private String xh;

    @Excel(name = "省份")
    private String sfdm;
}