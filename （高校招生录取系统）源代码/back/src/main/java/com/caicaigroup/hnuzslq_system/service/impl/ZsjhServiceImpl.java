package com.caicaigroup.hnuzslq_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caicaigroup.hnuzslq_system.common.Constants;
import com.caicaigroup.hnuzslq_system.entity.Zsjh;
import com.caicaigroup.hnuzslq_system.entity.vo.Query;
import com.caicaigroup.hnuzslq_system.entity.vo.ZsjhDetail;
import com.caicaigroup.hnuzslq_system.entity.vo.ZsjhLineObj;
import com.caicaigroup.hnuzslq_system.exception.ServiceException;
import com.caicaigroup.hnuzslq_system.mapper.ZdMapper;
import com.caicaigroup.hnuzslq_system.mapper.ZsjhMapper;
import com.caicaigroup.hnuzslq_system.mapper.ZydmMapper;
import com.caicaigroup.hnuzslq_system.service.ZsjhService;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ZsjhServiceImpl extends ServiceImpl<ZsjhMapper, Zsjh> implements ZsjhService {

    @Autowired
    private ZsjhMapper zsjhMapper;

    @Autowired
    private ZdMapper zdMapper;

    @Autowired
    private ZydmMapper zydmMapper;

    @Override
    public Boolean updateRenShu(Zsjh zsjh) {

        Zsjh zsjh1 = zsjhMapper.selectByPrimaryKey(zsjh.getTZsjhId());
        int now = zsjh.getZyjhrs();
        if (zsjh1 != null) {
            now -= zsjh1.getSfjhrs();
        }
        now += zsjh.getSfjhrs(); // 获得最新的该省该类计划总人数

        if (now < 0) {
            throw new ServiceException(Constants.CODE_401, "招生计划人数需大于0");
        }

        QueryWrapper<Zsjh> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("zydh", zsjh.getZydh());
        queryWrapper2.eq("jhlbdm", zsjh.getJhlbdm());
        queryWrapper2.eq("kldm", zsjh.getKldm());
        queryWrapper2.eq("year", zsjh.getYear());
        Zsjh zsjh2 = new Zsjh();
        zsjh2.setZyjhrs(now);

        if (zsjh1 == null) {
            save(zsjh);
        } else {
            zsjhMapper.updateByPrimaryKey(zsjh);
        }

        zsjhMapper.update(zsjh2, queryWrapper2); // 更新各个省保存的该专业该计划该科类相应年份的总人数
        return true;
    }

    @Override
    public IPage<ZsjhDetail> search(int current, int size , Query query) {
        Page<ZsjhDetail> page = new Page<>(current, size);
        try {
            IPage<ZsjhDetail> zsjhDetailIPage = null;
            if (query.getJh() == null || "".equals(query.getJh())) {
                zsjhDetailIPage = zsjhMapper.selectZsjhByQueryAllJh(page, query);
            } else {
                zsjhDetailIPage = zsjhMapper.selectZsjhByQuery(page, query);
            }
            return zsjhDetailIPage;
        } catch (Exception e) {
          e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ZsjhLineObj> selectWithoutPagnition(Query query) {
        Page<ZsjhDetail> page = new Page<>(1, 10000);
        if (query.getJh() == null || "".equals(query.getJh())) {
            IPage<ZsjhDetail> zsjhDetailIPage = zsjhMapper.selectZsjhByQueryAllJh(page, query);
            return getZsjhLineObjsByZsjhDetail(zsjhDetailIPage.getRecords());

        } else {
            IPage<ZsjhDetail> zsjhDetailIPage2 = zsjhMapper.selectZsjhByQuery(page, query);
            return getZsjhLineObjsByZsjhDetail(zsjhDetailIPage2.getRecords());
        }
    }

    private List<ZsjhLineObj> getZsjhLineObjsByZsjhDetail(List<ZsjhDetail> records) {
        List<ZsjhLineObj> zsjhLineObjs = new ArrayList<>();
        String nowZydh = "initial";
        for (ZsjhDetail zsjhDetail : records) {
            if (! nowZydh.equals(zsjhDetail.getZydh())) { // 新创建
                ZsjhLineObj zsjhLineObj = new ZsjhLineObj();
                BeanUtils.copyProperties(zsjhDetail, zsjhLineObj);
                zsjhLineObjs.add(zsjhLineObj);
                nowZydh = zsjhDetail.getZydh();
            }
            for (ZsjhLineObj zsjhLineObj : zsjhLineObjs) { // 加入
                if (zsjhLineObj.getZydh().equals(zsjhDetail.getZydh())) {
                    Map<String, String> sfdmAndsfjhrs = zsjhLineObj.getSfdmAndsfjhrs();
                    sfdmAndsfjhrs.put(zsjhDetail.getSfdm(), zsjhDetail.getSfjhrs());
                    zsjhLineObj.setSfdmAndsfjhrs(sfdmAndsfjhrs);
                }
            }
        }
        return zsjhLineObjs;
    }
}
