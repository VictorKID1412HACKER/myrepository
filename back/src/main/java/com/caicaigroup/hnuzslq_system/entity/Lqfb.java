package com.caicaigroup.hnuzslq_system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 录取发布
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@TableName("t_lqfb")
public class Lqfb {

    @TableId
    private String ksh; // 考生号

    private String xm; // 姓名
    private String sfz6; // 身份证后六位
    private String yxmc; // 院系名称
    private String zymc; // 专业名称
    private String emsbdh; // EMS备单号

}