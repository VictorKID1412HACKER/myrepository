package com.caicaigroup.hnuzslq_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caicaigroup.hnuzslq_system.entity.Zd;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ZdMapper extends BaseMapper<Zd> {

    int deleteByPrimaryKey(Integer tdZdbId);

    int insert(Zd record);

    int insertSelective(Zd record);

    Zd selectByPrimaryKey(Integer tdZdbId);

    int updateByPrimaryKeySelective(Zd record);

    int updateByPrimaryKey(Zd record);

    String getSfdmBySf(String sf);

    String selectDmByDmmcAndLbBs(Map<String, String> map);
}