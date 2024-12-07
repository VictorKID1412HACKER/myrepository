package com.caicaigroup.hnuzslq_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caicaigroup.hnuzslq_system.entity.Zsjh;
import com.caicaigroup.hnuzslq_system.entity.vo.Query;
import com.caicaigroup.hnuzslq_system.entity.vo.ZsjhDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ZsjhMapper extends BaseMapper<Zsjh> {
    IPage<ZsjhDetail> selectZsjhByQuery(Page<ZsjhDetail> page, Query query);

    int deleteByPrimaryKey(Integer tZsjhId);

    int insert(Zsjh record);

    int insertSelective(Zsjh record);

    Zsjh selectByPrimaryKey(Integer tZsjhId);

    int updateByPrimaryKeySelective(Zsjh record);

    int updateByPrimaryKey(Zsjh record);

    IPage<ZsjhDetail> selectZsjhByQueryAllJh(Page<ZsjhDetail> page, Query query);
}