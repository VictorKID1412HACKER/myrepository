package com.caicaigroup.hnuzslq_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caicaigroup.hnuzslq_system.entity.Zydm;
import com.caicaigroup.hnuzslq_system.entity.vo.ZdKeyAndVal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ZydmMapper extends BaseMapper<Zydm> {

    int deleteByPrimaryKey(Integer tZydmId);

    int insert(Zydm record);

    int insertSelective(Zydm record);

    Zydm selectByPrimaryKey(Integer tZydmId);

    int updateByPrimaryKeySelective(Zydm record);

    int updateByPrimaryKey(Zydm record);

    String selectZymcByZmZydh(String zydh, String year);

    List<ZdKeyAndVal> selectKeyAndValByYx(Map<String, String> map);

    String selectYxmcByZy(Map<String, String> map);

    String selectZymcByZy(Map<String, String> map);

    String selectZydhByZymcAndYear(Map<String, String> map);
}