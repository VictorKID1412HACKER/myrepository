package com.caicaigroup.hnuzslq_system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 考生信息与二进制照片
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@TableName("t_ksxx")
public class KsxxWithBLOBs extends Ksxx {
    private byte[] kszp; // 考生照片
    private byte[] dazp1; // 档案照片1
    private byte[] dazp2; // 档案照片2
    private byte[] dazp3; // 档案照片3

}