package com.caicaigroup.hnuzslq_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caicaigroup.hnuzslq_system.entity.Ksxx;
import com.caicaigroup.hnuzslq_system.entity.Lqfb;
import com.caicaigroup.hnuzslq_system.entity.Zydm;
import com.caicaigroup.hnuzslq_system.mapper.KsxxMapper;
import com.caicaigroup.hnuzslq_system.mapper.LqfbMapper;
import com.caicaigroup.hnuzslq_system.mapper.ZdMapper;
import com.caicaigroup.hnuzslq_system.mapper.ZydmMapper;
import com.caicaigroup.hnuzslq_system.service.LqfbService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 21g
 * @version 1.0
 * @className LqfbServiceImpl
 * @since 1.0
 */
@Service
public class LqfbServiceImpl extends ServiceImpl<LqfbMapper, Lqfb> implements LqfbService {

    @Autowired
    private KsxxMapper ksxxMapper;

    @Autowired
    private ZdMapper zdMapper;

    @Autowired
    private ZydmMapper zydmMapper;

    @Autowired
    private LqfbMapper lqfbMapper;

    @Override
    public void insertlqfbByCondition(Map<String, String> map) {
        QueryWrapper<Ksxx> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("lqzydm");
        //    queryWrapper.eq("sfdm", map.get("sf"));
        queryWrapper.eq("sfdm", zdMapper.getSfdmBySf(map.get("sf")));
        List<Ksxx> ksxxList = ksxxMapper.selectList(queryWrapper);
        String startEms = map.get("startEms");
        String bf = startEms.substring(0, 2);
        String af = startEms.substring(startEms.length() - 2);
        Long i = Long.valueOf(startEms.substring(2, 11));
        int num = Integer.parseInt(map.get("num"));
        int now = 0;

        for (Ksxx ksxx : ksxxList) {

            QueryWrapper<Lqfb> queryWrapperExist = new QueryWrapper<>();
            queryWrapperExist.eq("ksh", ksxx.getKsh());
            if (lqfbMapper.selectOne(queryWrapperExist) != null){
                continue;
            }

            Lqfb lqfb = new Lqfb();
            lqfb.setKsh(ksxx.getKsh());
            lqfb.setSfz6(ksxx.getZjhm().substring(ksxx.getZjhm().length() - 6));
            lqfb.setXm(ksxx.getXm());
            Map<String, String> mapQy = new HashMap<>();
            mapQy.put("year", "2021");
            mapQy.put("zydh", ksxx.getLqzydm());
            lqfb.setYxmc(zydmMapper.selectYxmcByZy(mapQy));
            QueryWrapper<Zydm> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("zydh", ksxx.getLqzydm());
            queryWrapper1.eq("year", 2021);
            List<Zydm> zydms = zydmMapper.selectList(queryWrapper1);
            String zymc = zydms.get(0).getZymc();
            lqfb.setZymc(zymc);
            lqfb.setEmsbdh(bf + i + af);
            saveOrUpdate(lqfb);
            i ++;

            now ++;
            if (now == num) {
                return;
            }
        }

    }

}
