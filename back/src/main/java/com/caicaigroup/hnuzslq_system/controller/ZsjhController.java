package com.caicaigroup.hnuzslq_system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caicaigroup.hnuzslq_system.common.Constants;
import com.caicaigroup.hnuzslq_system.common.Result;
import com.caicaigroup.hnuzslq_system.entity.Zsjh;
import com.caicaigroup.hnuzslq_system.entity.vo.Query;
import com.caicaigroup.hnuzslq_system.entity.vo.ZsjhDetail;
import com.caicaigroup.hnuzslq_system.entity.vo.ZsjhLineObj;
import com.caicaigroup.hnuzslq_system.exception.ServiceException;
import com.caicaigroup.hnuzslq_system.service.ZsjhService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 招生计划
 */
@RestController
@RequestMapping("/zsjh")
public class ZsjhController {

    @Autowired
    private ZsjhService zsjhService;

    /**
     * 制定招生计划
     * @param zsjh 招生计划
     * @return
     */
    @PostMapping("/makePlan")
    public Result makePlan(@RequestBody Zsjh zsjh) {
        if (zsjh.getZyjhrs() == null || "".equals(zsjh.getZyjhrs())) {
            throw new ServiceException(Constants.CODE_400, "传参错误");
        }
        if (zsjh.getYear() == null || "".equals(zsjh.getYear())) {
            throw new ServiceException(Constants.CODE_400, "传参错误");
        }
        if (zsjh.getJhlbdm() == null || "".equals(zsjh.getJhlbdm())) {
            throw new ServiceException(Constants.CODE_400, "请选择具体计划类别");
        }
        if (zsjhService.updateRenShu(zsjh)) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 刷新查询
     * @param current 当前页数
     * @param size 页面大小
     * @param query 查询条件
     * @return
     */
    @GetMapping
    public Result search( Integer current, Integer size, Query query){
        if (current == null || size == null) {
            throw new ServiceException(Constants.CODE_400, "分页参数错误");
        }
        if (query.getNf() == null || "".equals(query.getNf())) {
            Calendar cal = Calendar.getInstance();
            Integer year = cal.get(Calendar.YEAR);
            query.setNf(year.toString());
        }
        IPage<ZsjhDetail> list = zsjhService.search(current, size, query);
        return  Result.success(list);
    }

    /**
     * 非分页查询招生计划
     * @param query 查询条件
     * @return
     */
    @GetMapping("/searchWithoutPagnition")
    public Result searchWithoutPagnition(Query query){
        if (query.getNf() == null || "".equals(query.getNf())) {
            query.setNf("2021");
        }
        List<ZsjhLineObj> list = zsjhService.selectWithoutPagnition(query);
        return  Result.success(list);
    }

}
