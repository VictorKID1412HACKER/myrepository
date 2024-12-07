package com.caicaigroup.hnuzslq_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caicaigroup.hnuzslq_system.entity.Lqfb;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LqfbMapper extends BaseMapper<Lqfb> {

    int insert(Lqfb record);

    int insertSelective(Lqfb record);
}