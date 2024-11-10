package com.caicaigroup.hnuzslq_system.controller;

import com.caicaigroup.hnuzslq_system.common.Result;
import com.caicaigroup.hnuzslq_system.service.TjfxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tjfx")
public class TjfxController {

    @Autowired
    private TjfxService tjfxService;

    /**
     * 按省份返回专业第一志愿报考率
     * @return
     */
    @GetMapping("/firstEnrollRateBySf")
    public Result firstEnrollRateBySf(){
        List<Map<String, Object>> listMap = tjfxService.firstEnrollRateBySf();
        return Result.success(listMap);
    }

    /**
     * 按院系返回专业第一志愿录取率
     * @return
     */
    @GetMapping("/firstAdmitRateByYx")
    public Result firstAdmitRateBySf(){
        List<Map<String, Object>> listMap = tjfxService.firstAdmitRateByYx();
        return Result.success(listMap);
    }

    /**
     * 按省份返回志愿报考率
     * @return
     */
    @GetMapping("/enrollRateBySf")
    public Result enrollRateBySf(){
        List<Map<String, Object>> listMap = tjfxService.enrollRateBySf();
        return Result.success(listMap);
    }
}
