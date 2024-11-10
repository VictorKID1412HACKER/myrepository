package com.caicaigroup.hnuzslq_system.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * 班级
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bj {
    private String yx; // 院系
    private String zy; // 专业
    private String bjmc; // 班级名称
    private int xssl; // 学生数量
    private int mnum; // 男生人数
    private int fnum; // 女生人数
    private String bjdh; // 班级代号

    private ArrayList<String> kshList; // 考生号列表
}
