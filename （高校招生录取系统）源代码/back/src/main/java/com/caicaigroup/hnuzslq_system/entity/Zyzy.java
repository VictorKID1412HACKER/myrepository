package com.caicaigroup.hnuzslq_system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 专业志愿
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@TableName("t_zyzy")
public class Zyzy {
    private String ksh; // 考生号
    private String zydh; // 专业代号
    private Long zyxh; // 专业型号
}