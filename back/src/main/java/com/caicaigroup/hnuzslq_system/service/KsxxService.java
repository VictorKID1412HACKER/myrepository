package com.caicaigroup.hnuzslq_system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caicaigroup.hnuzslq_system.entity.Fbqk;
import com.caicaigroup.hnuzslq_system.entity.Ksxx;
import com.caicaigroup.hnuzslq_system.entity.KsxxWithBLOBs;
import com.caicaigroup.hnuzslq_system.entity.vo.KsFb;
import com.caicaigroup.hnuzslq_system.entity.vo.KsxxUpateInfo;
import com.caicaigroup.hnuzslq_system.entity.vo.Lqfsx;
import com.caicaigroup.hnuzslq_system.entity.vo.Query;
import com.caicaigroup.hnuzslq_system.entity.vo.StuQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface KsxxService {
    /**
     * 上载考生信息
     * @param files
     * @return
     */
    boolean uploadKsxx(MultipartFile[] files, String sf);

    /**
     * 甘肃：将数据库的临时表存入正式表中
     * @param sf 省份
     * @param jhlb 计划类别
     * @param kl 科类
     * @return
     */
    boolean gsTddBmkToKsxx(String sf,String jhlb,String kl);

    /**
     * 湖南:将数据库的临时表存入正式表中
     * @param sf 省份
     * @param jhlb 计划类别
     * @param kl 科类
     * @return
     */
    boolean hnTddBmkToKsxx(String sf,String jhlb,String kl);

    /**
     * 上海:将数据库的临时表存入正式表中
     * @param sf 省份
     * @param jhlb 计划类别
     * @param kl 科类
     * @return
     */
    boolean shTddBmkToKsxx (String sf,String jhlb,String kl);

    /**
     * 根据三条件获取考生信息（中文）
     * @param sf 省份
     * @param jhlb 计划类别
     * @param kl 科类
     * @return 考生信息集合
     */
    List<Ksxx> CheckKsxxCN(String sf,String jhlb,String kl);

    /**
     * 上载考生照片
     * @param files 文件
     * @param zplx 照片类型 考生照片 档案照片1 档案照片2 档案照片3
     * @return 照片数量
     */
    Integer uploadPhoto(MultipartFile[] files,String zplx);

    /**
     * 获取ksxx表中该省份考生数量
     * @param sf
     * @return ksxx表中该省份考生数量
     */
    Integer getSfKsxxAmount(String sf);

    /**
     * 获取考生照片
     * @param ksh 考生号
     * @return 照片二进制串
     */
    byte[] getKszp(String ksh);

    /**
     * 分页条件查询
     * @param current 当前页面
     * @param size 分页条数
     * @param stuQuery 查询条件
     * @return
     */
    IPage<KsFb> search(int current, int size, StuQuery stuQuery);

    /**
     * 更新班级代号
     * @param kshList 考生号
     * @param bjdh 班级代号
     */
    void updateBjdh(List<String> kshList, String bjdh);

    /**
     * 查询班级列表
     * @return
     */
    Map<String, Object> selectBjList();

    /**
     * 设置分班后的学号
     * @param fbqk 分班情况
     */
    void setfb(Fbqk fbqk);

    /**
     * 更新考生信息
     * @param ksxx 考生信息
     */
    void updateKsxx(KsxxWithBLOBs ksxx);

    /**
     * 更新考生信息2.0
     * @param ksxxUpateInfo 包装的考生信息
     */
    void updateStuInfo(KsxxUpateInfo ksxxUpateInfo);

    /**
     * 查询录取分数线
     * @param current 当前页数
     * @param size 页面数目大小
     * @param query 查询条件
     * @return
     */
    IPage<Lqfsx> selectLqfsx(Integer current, Integer size, Query query);

    /**
     * 根据考生号获取单个考生的所有信息 （中文）
     * @param ksh
     * @return
     */
    Ksxx getKsxxByKsh(String ksh);

    /**
     * 返回中文转换后的考生信息
     * @return
     */
    List<Ksxx> ksxxInfoCN();

    /**
     * 考生信息压缩包下载
     * @return
     * @throws IOException
     */
    void ksxxZipDownload(HttpServletResponse response) throws IOException;

    /**
     * 查询五个人数最多和最少的专业及人数
     * @return
     */
    List<Map<String, Object>> largestAndLeastZy();

    /**
     * 打包下载考生照片
     * @param response
     */
    void kszpZipDownload(HttpServletResponse response);

    /**
     * 打包下载档案照片
     * @param response
     */
    void dazpZipDownload(HttpServletResponse response);
}
