package com.caicaigroup.hnuzslq_system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caicaigroup.hnuzslq_system.entity.Fbqk;
import com.caicaigroup.hnuzslq_system.entity.vo.Bj;
import com.caicaigroup.hnuzslq_system.entity.vo.StuQuery;
import com.caicaigroup.hnuzslq_system.mapper.FbqkMapper;
import com.caicaigroup.hnuzslq_system.service.FbqkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class FbqkServiceImpl extends ServiceImpl<FbqkMapper, Fbqk> implements FbqkService {

    @Autowired
    private FbqkMapper fbqkMapper;

    @Override
    public void insertByBatch(List<Fbqk> list) {
        for (Fbqk fbqk : list) {
            fbqkMapper.insert(fbqk);
        }
    }

    @Override
    public List<String> getBjdhList(String zymc) {
        return fbqkMapper.selectBjdhListByZymc(zymc);
    }

    @Override
    public IPage<Bj> searchFb(Integer current, Integer size, StuQuery stuQuery) {
        Page<Bj> page = new Page<>(current, size);
        IPage<Bj> BjIPage = fbqkMapper.selectBjs(page, stuQuery);
        return BjIPage;
    }

    @Override
    public void deleteAll() {
        fbqkMapper.delete(null);
    }
}
