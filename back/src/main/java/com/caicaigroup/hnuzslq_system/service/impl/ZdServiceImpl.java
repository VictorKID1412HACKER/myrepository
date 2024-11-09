package com.caicaigroup.hnuzslq_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caicaigroup.hnuzslq_system.entity.Zd;
import com.caicaigroup.hnuzslq_system.entity.Zydm;
import com.caicaigroup.hnuzslq_system.entity.vo.ZdKeyAndVal;
import com.caicaigroup.hnuzslq_system.mapper.ZdMapper;
import com.caicaigroup.hnuzslq_system.mapper.ZydmMapper;
import com.caicaigroup.hnuzslq_system.service.ZdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 21g
 * @version 1.0
 * @className ZdServiceImpl
 * @since 1.0
 */
@Transactional
@Service
public class ZdServiceImpl extends ServiceImpl<ZdMapper, Zd> implements ZdService {

    @Autowired
    private ZdMapper zdMapper;

    @Autowired
    private ZydmMapper zydmMapper;

    @Override
    public List<ZdKeyAndVal> selectPairByType(String type) {
        QueryWrapper<Zd> queryWrapper = new QueryWrapper<>();
        List<ZdKeyAndVal> opts = new ArrayList<>();
        if ("专业".equals(type)) {
            List<Zydm> zydms = zydmMapper.selectList(null);
            opts = new ArrayList<>();
            for (Zydm zydm : zydms) {
                opts.add(new ZdKeyAndVal(zydm.getZydh(), zydm.getZymc()));
            }
            return opts;
        }
        if ("科类".equals(type)) {
            queryWrapper.eq("dmlb", "kldm");
        }
        if ("院系".equals(type)) {
            queryWrapper.eq("dmlb", "yxdm");
        }
        if ("计划类别".equals(type)) {
            queryWrapper.eq("dmlb", "jhlbdm");
        }
        if("省份".equals(type)){
            queryWrapper.eq("dmlb", "sfdm");
        }
        queryWrapper.eq("bs", "HD");
        queryWrapper.eq("yxx", "1");
        List<Zd> zdList = zdMapper.selectList(queryWrapper);
        for (Zd zd : zdList) {
            opts.add(new ZdKeyAndVal(zd.getDm(), zd.getDmmc()));
        }
        return opts;
    }
}
