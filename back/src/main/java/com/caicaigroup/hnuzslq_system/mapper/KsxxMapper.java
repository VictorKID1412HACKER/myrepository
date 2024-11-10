package com.caicaigroup.hnuzslq_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caicaigroup.hnuzslq_system.entity.Ksxx;
import com.caicaigroup.hnuzslq_system.entity.KsxxWithBLOBs;
import com.caicaigroup.hnuzslq_system.entity.vo.KsFb;
import com.caicaigroup.hnuzslq_system.entity.vo.Lqfsx;
import com.caicaigroup.hnuzslq_system.entity.vo.Query;
import com.caicaigroup.hnuzslq_system.entity.vo.StuQuery;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface KsxxMapper extends BaseMapper<Ksxx> {

    int deleteByPrimaryKey(String ksh);

    int insertSelective(KsxxWithBLOBs record);

    KsxxWithBLOBs selectByPrimaryKey(String ksh);

    int updateByPrimaryKeySelective(KsxxWithBLOBs record);
    // 这俩功能重复
    int updateByPrimaryKeyWithBLOBs(KsxxWithBLOBs record);

    int updateByPrimaryKey(Ksxx record);

    IPage<KsFb> selectKsByFb(Page<KsFb> page, Query query);

    List<Ksxx> selectListByLqzy(String zydh);

    IPage<KsFb> selectDetail(Page<KsFb> page, StuQuery stuQuery);

    void updateBjdhAndXh(KsxxWithBLOBs ksxxWithBLOBs);

    IPage<Lqfsx> selectLqfsx(Page<Lqfsx> page, Query query);

    /**
     * 根据考生号获取单个考生数据
     * @param ksh
     * @return
     */
    Ksxx selectByKsh(String ksh);

    /**
     * 修改省份代码
     * @param sf 省份中文名
     * @return
     */
    int updateSfdm(String sf);

    /**
     * 修改考生信息中的照片
     *
     * @param record KsxxWithBLOBs
     * @return
     */
    int updateKsxxZp(KsxxWithBLOBs record);

    /**
     * 根据考生号获取考生照片
     *
     * @param ksh
     * @return
     */
    byte[] selectByKshKszp(String ksh)throws Exception;

    /**
     * 获得该省份考生数量
     *
     * @param sf
     * @return
     */
    int selectBySfdm(String sf);

    /**
     * 获得所有考生信息（转换为中文）
     *
     * @return
     */
    List<Ksxx> selectksxxCN();

    /**
     * 获得录取专业中文及数量
     * @return
     */
    @MapKey("zymc")
    List<Map<String, Object>> selectCountZyCN();

    /**
     * 获得考生学号，专业，班级，考生照片，专业名称
     * @return
     */
    @MapKey("key")
    List<Map<String, Object>> selectKsZp();

    /**
     * 获得考生学号，专业，班级，档案照片，专业名称
     * @return
     */
    @MapKey("key")
    List<Map<String, Object>> selectdazp();
}