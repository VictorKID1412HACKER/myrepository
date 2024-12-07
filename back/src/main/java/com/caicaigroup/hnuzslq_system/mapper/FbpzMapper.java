package com.caicaigroup.hnuzslq_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caicaigroup.hnuzslq_system.entity.Fbpz;
import com.caicaigroup.hnuzslq_system.entity.vo.FbpzDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FbpzMapper extends BaseMapper<Fbpz> {

    int deleteByPrimaryKey(Integer tFbpzId);

    int insert(Fbpz record);

    int insertSelective(Fbpz record);

    Fbpz selectByPrimaryKey(Integer tFbpzId);

    int updateByPrimaryKeySelective(Fbpz record);

    int updateByPrimaryKey(Fbpz record);

    List<FbpzDetail> selectFbpzDetails();

}