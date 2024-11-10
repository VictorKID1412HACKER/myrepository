package com.caicaigroup.hnuzslq_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caicaigroup.hnuzslq_system.entity.Fbqk;
import com.caicaigroup.hnuzslq_system.entity.vo.Bj;
import com.caicaigroup.hnuzslq_system.entity.vo.FbpzDetail;
import com.caicaigroup.hnuzslq_system.entity.vo.StuQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FbqkMapper extends BaseMapper<Fbqk> {

    int insert(Fbqk record);

    int insertSelective(Fbqk record);

    int insertByAutoFb(FbpzDetail autoFb);

    List<String> selectBjdhListByZymc(String zymc);

    IPage<Bj> selectBjs(Page<Bj> page, StuQuery stuQuery);
}