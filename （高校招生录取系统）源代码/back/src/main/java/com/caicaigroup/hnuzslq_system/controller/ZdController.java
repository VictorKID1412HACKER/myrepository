package com.caicaigroup.hnuzslq_system.controller;

import com.caicaigroup.hnuzslq_system.common.Constants;
import com.caicaigroup.hnuzslq_system.entity.vo.ZdKeyAndVal;
import com.caicaigroup.hnuzslq_system.exception.ServiceException;
import com.caicaigroup.hnuzslq_system.service.ZdService;
import com.caicaigroup.hnuzslq_system.service.ZydmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zd")
public class ZdController {

    @Autowired
    private ZdService zdService;

    @Autowired
    private ZydmService zydmService;

    /**
     * 根据字典类型获得所有的选择
     * @param type 类型
     * @return
     */
    @GetMapping("/getAllOptByType")
    public List<ZdKeyAndVal> getAllOptByType(String type){
        if (type == null || "".equals(type)) {
            throw new ServiceException(Constants.CODE_400, "传参错误");
        }
        List<ZdKeyAndVal> opts = zdService.selectPairByType(type);
        return opts;
    }

    /**
     * 根据院系获得专业
     * @param map 专业名称与代码
     * @return
     */
    @GetMapping("/getZyByYx")
    public List<ZdKeyAndVal> getZyByYx(@RequestParam Map<String, String> map) {
        List<ZdKeyAndVal> opts = zydmService.selectZyByYx(map);
        return opts;
    }
}
