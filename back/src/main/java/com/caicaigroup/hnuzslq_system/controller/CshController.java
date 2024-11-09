package com.caicaigroup.hnuzslq_system.controller;

import com.caicaigroup.hnuzslq_system.common.Result;
import com.caicaigroup.hnuzslq_system.utils.FileUtil;
import com.caicaigroup.hnuzslq_system.utils.MysqlUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 初始化系统
 */
@RestController
@RequestMapping("/csh")
public class CshController {

    /**
     * 备份系统
     * @return
     */
    @GetMapping("/backUp")
    public Result backUp(){
        MysqlUtil.backUpData();
        return  Result.success();
    }

    /**
     * 初始化系统
     * @return
     */
    @GetMapping("/initialize")
    public Result initialize(){
        FileUtil.cshDeleteFile();
        return  Result.success();
    }
}
