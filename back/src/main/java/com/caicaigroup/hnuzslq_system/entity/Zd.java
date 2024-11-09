package com.caicaigroup.hnuzslq_system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@TableName("td_zdb")
public class Zd {
    private Integer tdZdbId; // 自增id
    private String dmlb; // 代码类别
    private String dm; // 代码
    private String dmmc; // 代码名称
    private Boolean yxx; // 有效性
    private Date sxsj; // 生效时间
    private Date zzsj; // 终止时间
    private String bs; // 标识
}