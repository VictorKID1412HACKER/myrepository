package com.caicaigroup.hnuzslq_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caicaigroup.hnuzslq_system.entity.Zydm;
import com.caicaigroup.hnuzslq_system.entity.vo.ZdKeyAndVal;
import com.caicaigroup.hnuzslq_system.mapper.ZydmMapper;
import com.caicaigroup.hnuzslq_system.service.ZydmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 21g
 * @version 1.0
 * @className ZydmServiceImpl
 * @since 1.0
 */
@Transactional
@Service
public class ZydmServiceImpl extends ServiceImpl<ZydmMapper, Zydm> implements ZydmService {

    @Autowired
    private ZydmMapper zydmMapper;

    @Override
    public List<ZdKeyAndVal> selectZyByYx(Map<String, String> map) {
        List<ZdKeyAndVal> zys = zydmMapper.selectKeyAndValByYx(map);
        return zys;
    }
}
