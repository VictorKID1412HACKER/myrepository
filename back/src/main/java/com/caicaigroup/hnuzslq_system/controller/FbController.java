package com.caicaigroup.hnuzslq_system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caicaigroup.hnuzslq_system.common.Constants;
import com.caicaigroup.hnuzslq_system.common.Result;
import com.caicaigroup.hnuzslq_system.entity.Fbqk;
import com.caicaigroup.hnuzslq_system.entity.vo.Bj;
import com.caicaigroup.hnuzslq_system.entity.vo.KsFb;
import com.caicaigroup.hnuzslq_system.entity.vo.StuQuery;
import com.caicaigroup.hnuzslq_system.exception.ServiceException;
import com.caicaigroup.hnuzslq_system.service.FbpzService;
import com.caicaigroup.hnuzslq_system.service.FbqkService;
import com.caicaigroup.hnuzslq_system.service.KsxxService;
import com.caicaigroup.hnuzslq_system.service.UserService;
import com.caicaigroup.hnuzslq_system.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分班
 */
@RestController
@RequestMapping("/fb")
public class FbController {

    @Autowired
    private KsxxService ksxxService;

    @Autowired
    private FbqkService fbqkService;

    @Autowired
    private FbpzService fbpzService;

    @Autowired
    private UserService userService;

    private static List<Fbqk> fbqkTempList = new ArrayList<>();

    /**
     * 刷新查询
     * @param current 当前页
     * @param size 页面显示条数
     * @param stuQuery 查询条件
     * @return
     */
    @GetMapping
    public Result search(Integer current, Integer size, StuQuery stuQuery){
        if (current == null || size == null) {
            throw new ServiceException(Constants.CODE_400, "分页参数错误");
        }
        if (stuQuery.getNf() == null || "".equals(stuQuery.getNf())) {
            stuQuery.setNf("2021");
        }
        if (stuQuery.getBj() != null && !("".equals(stuQuery.getBj()))) {
            String bj = stuQuery.getBj();
            bj = bj.substring(0, bj.length() - 1);
            if (bj.length() == 1) {
                bj = "0" + bj;
            }
            stuQuery.setBj(bj);
        }
        IPage<KsFb> list = ksxxService.search(current, size, stuQuery);
        return  Result.success(list);
    }

    /**
     * 查看分班班级情况
     * @param current 当前页
     * @param size 页面显示条数
     * @param stuQuery 查询条件
     * @return
     */
    @GetMapping("/bj")
    public Result searchFb(Integer current, Integer size, StuQuery stuQuery){
        if (current == null || size == null) {
            throw new ServiceException(Constants.CODE_400, "分页参数错误");
        }
        if (stuQuery.getNf() == null || "".equals(stuQuery.getNf())) {
            stuQuery.setNf("2021");
        }
        IPage<Bj> list = fbqkService.searchFb(current, size, stuQuery);
        return  Result.success(list);
    }

    /**
     * 获得手动分班班级号列表
     * @param zymc 专业名称
     * @return
     */
    @GetMapping("/manmade/getBjdhList")
    public Result getBjdhList(String zymc){
        if (zymc == null || "".equals(zymc)) {
            throw new ServiceException(Constants.CODE_400, "参数错误");
        }
        List<String> dhList = fbqkService.getBjdhList(zymc);
        return Result.success(dhList);
    }

    /**
     * 选择分班班级
     * @param map 考生号与班级代号
     * @return
     */
    @PostMapping("/setFb")
    public Result setFb(@RequestBody Map<String,Object> map){
        List<String> kshList = (List<String>) map.get("kshList");
        String bjdh = String.valueOf(map.get("bjdh"));
        ksxxService.updateBjdh(kshList, bjdh);
        return Result.success();
    }

    /**
     * 自动分班
     * @return
     */
    @GetMapping("/automaticShift")
    public Result automaticShift(){
        Map<String, Object> map = ksxxService.selectBjList();
        fbqkTempList = (List<Fbqk>) map.get("fbqks");

        return Result.success(map.get("bjs"));
    }

    /**
     * 分班确认
     * @return
     */
    @GetMapping("/automaticShift/confirm")
    public Result automaticShiftConfirm(){
        fbqkService.deleteAll();
        fbqkService.insertByBatch(fbqkTempList);
        for (Fbqk fbqk : fbqkTempList) {
            ksxxService.setfb(fbqk);
        }
        return Result.success();
    }

    /**
     * 上传分班配置
     * @param files 文件列表
     * @return
     */
    @PostMapping("/upload")
    public Result uploadFb(@RequestParam("files") MultipartFile[] files){
        if (files.length != 1) {
            throw new ServiceException(Constants.CODE_400, "仅允许上传当年分班配置");
        }
        String id=userService.getCurrentUser().getId().toString();
        String s = FileUtil.uploadFiles(files,id);
        System.out.println(s);
        fbpzService.importExcel(files[0].getOriginalFilename());
        return Result.success();
    }


}
