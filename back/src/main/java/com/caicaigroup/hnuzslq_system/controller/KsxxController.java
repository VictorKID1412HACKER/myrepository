package com.caicaigroup.hnuzslq_system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caicaigroup.hnuzslq_system.common.Constants;
import com.caicaigroup.hnuzslq_system.common.Result;
import com.caicaigroup.hnuzslq_system.entity.Ksxx;
import com.caicaigroup.hnuzslq_system.entity.KsxxWithBLOBs;
import com.caicaigroup.hnuzslq_system.entity.vo.KsxxUpateInfo;
import com.caicaigroup.hnuzslq_system.entity.vo.Lqfsx;
import com.caicaigroup.hnuzslq_system.entity.vo.Query;
import com.caicaigroup.hnuzslq_system.exception.ServiceException;
import com.caicaigroup.hnuzslq_system.mapper.TableMapper;
import com.caicaigroup.hnuzslq_system.service.KsxxService;
import com.caicaigroup.hnuzslq_system.service.LqfbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 考生信息
 */
@RestController
@RequestMapping("/ksxx")
public class KsxxController {

    @Autowired
    private KsxxService ksxxService;

    @Autowired
    private LqfbService lqfbService;

    @Autowired
    private TableMapper tableMapper;

    /**
     * 1.上传考生信息并入库（tdd bmk表）
     * @return
     */
    @PostMapping("/upload")
    public Result uploadKsxx(@RequestParam("files") MultipartFile[] files,
                             @RequestParam("sf") String sf){
        if(files==null || sf == null || "".equals(sf)){
            throw new ServiceException(Constants.CODE_400,"传参错误");
        }
        if(files.length>3){
            throw new ServiceException(Constants.CODE_401,"最多允许上传3个文件");
        }
        Set<String> fileNameSet = new HashSet();

        for(int i=0;i<files.length;i++) {
            String fileName = files[i].getOriginalFilename(); // 获取文件名
            String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
            fileNameSet.add(fileName);
            if(!suffix.equals("dbf")){
                throw new ServiceException(Constants.CODE_401,"文件上传有误，仅允许上传dbf类型文件");
            }
        }
        System.out.println(fileNameSet);

        // 简单判断上传文件是否符合上载流程需要的tdd表或bmk表 (粗糙)
        if("甘肃".equals(sf)) {
            if(files.length!=1 || !files[0].getOriginalFilename().contains("tdd")){
                throw new ServiceException(Constants.CODE_401,"文件上传有误，请上传t_tdd.dbf");
            }
        }
        if("湖南".equals(sf)||"上海".equals(sf)) {
            if(files.length!=3 || !fileNameSet.contains("t_bmk.dbf") ||
                    !fileNameSet.contains("T_TDD.dbf") || !fileNameSet.contains("t_zykmx.dbf")){
                throw new ServiceException(Constants.CODE_401,"文件上传有误,请上传T_TDD.dbf,t_bmk.dbf和t_zykmx.dbf");
            }
        }

        boolean flag=ksxxService.uploadKsxx(files,sf);
        if(flag==false) {
            throw new ServiceException(Constants.CODE_500, "程序错误，数据入库失败！");
        }

        return Result.success("数据成功入库");
    }

    /**
     * 2.一键审核
     * 将数据库的临时表存入正式表中
     * @return
     */
    @GetMapping("/audit")
    public Result TddBmkToKsxx(@RequestParam("sf") String sf,
                               @RequestParam("jhlb") String jhlb,
                               @RequestParam("kl") String kl){
        if(sf == null || "".equals(sf)||jhlb == null || "".equals(jhlb)||kl == null || "".equals(kl)){
            throw new ServiceException(Constants.CODE_400,"传参错误");
        }
        boolean flag=false;
        if("甘肃".equals(sf)){
            flag=ksxxService.gsTddBmkToKsxx(sf,jhlb,kl);
        }if("湖南".equals(sf)){
            flag=ksxxService.hnTddBmkToKsxx(sf,jhlb,kl);
        }if("上海".equals(sf)){
            flag=ksxxService.shTddBmkToKsxx(sf,jhlb,kl);
        }
        if(flag==true){
            return Result.success("审核通过");
        }else{
            return Result.error(Constants.CODE_500,"系统错误");
        }
    }

    /**
     * 3. 查看审核结果
     * 展示 ksxx表
     */
    @GetMapping("/checkKsxx")
    public Result checkKsxx(@RequestParam("sf") String sf,
                            @RequestParam("jhlb") String jhlb,
                            @RequestParam("kl") String kl){
        if(sf == null || "".equals(sf)||jhlb == null || "".equals(jhlb)||kl == null || "".equals(kl)){
            throw new ServiceException(Constants.CODE_400,"传参错误");
        }
        List<Ksxx> ksxxList=ksxxService.CheckKsxxCN(sf,jhlb,kl);
        if (ksxxList != null) {
            if (ksxxList.size() > 0) {
                return Result.success(ksxxList);
            } else {
                return Result.error(Constants.CODE_500, "获取数据错误");
            }
        } else {
            return Result.error(Constants.CODE_500, "获取数据失败");
        }
    }



    @PostMapping("/test")
    public Result test(@RequestParam("files") MultipartFile[] files){
        try {
            tableMapper.updateShTdd("t_tdd_2");
            tableMapper.updateShZykmx("t_zykmx_2");
            tableMapper.shBmkInsertKsxx("t_bmk_2");
            tableMapper.shTddInsertKsxx("t_tdd_2","上海","普通(综合)","综合");
            tableMapper.shBmkInsertGkcjx("t_bmk_2");
            tableMapper.shZykmxInsertZyzy("t_zykmx_2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    /**
     * 清空表
     * @return
     */
    @GetMapping("/truncateTable")
    public Result truncateTable(){
        tableMapper.truncateTable("t_ksxx");
        tableMapper.truncateTable("t_gkcjx");
        tableMapper.truncateTable("t_zyzy");
        tableMapper.truncateTable("t_lqfb");
        return Result.success();
    }


    /**
     * 上传考生照片
     * @param files 文件
     * @param zplx 照片类型 考生照片 档案照片1 档案照片2 档案照片3
     * @return 上传成功数量
     */
    @PostMapping("/uploadPhoto")
    public Result uploadPhoto(@RequestParam("files") MultipartFile[] files,
                              @RequestParam("zplx") String zplx){
        if(files==null){
            throw new ServiceException(Constants.CODE_400,"传参错误");
        }
        Integer amount=ksxxService.uploadPhoto(files,zplx);
        return Result.success("成功上传照片"+amount+"张");
    }

    /**
     * 获取某省份已入库考生数量
     * @param sf 省份（中文）
     * @return 考生数量
     */
    @GetMapping("/getSfKsxxAmount")
    public Result getSfKsxxAmount(@RequestParam("sf") String sf){
        if(sf==null||"".equals(sf)){
            throw new ServiceException(Constants.CODE_400, "传参错误");
        }
        Integer amount=ksxxService.getSfKsxxAmount(sf);
        return Result.success(amount);
    }

    /**
     * 获取考生照片
     * @param ksh
     * @return
     */
    @RequestMapping(value = "/getKszp",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public Result getKszp(@RequestParam("ksh") String ksh){
        if(ksh==null || "".equals(ksh)){
            throw new ServiceException(Constants.CODE_400, "传参错误");
        }
        byte[] imageBlobs=ksxxService.getKszp(ksh);
        System.out.println("con:"+imageBlobs);
        if(imageBlobs==null){
            return Result.error(Constants.CODE_600,"该考生照片未入库！");
        }
        return Result.success();
    }

    @GetMapping("/getKsxxByksh")
    public Result getKsxxByksh(@RequestParam("ksh") String ksh){
        Ksxx ksxx=ksxxService.getKsxxByKsh(ksh);
        return Result.success(ksxx);
    }

    /**
     * 修改学生信息
     * @param ksxx 考生信息
     * @return
     */
    @PostMapping("/updateStu")
    public Result updateStu(@RequestBody KsxxWithBLOBs ksxx){
        if (ksxx.getKsh() == null || "".equals(ksxx.getKsh())) {
            throw new ServiceException(Constants.CODE_400, "传参错误");
        }
        ksxxService.updateKsxx(ksxx);
        return Result.success();
    }

    /**
     * 打印
     * @return
     */
    @GetMapping("/print")
    public Result print(){
        return Result.success();
    }

    /**
     * 生成ems被单号，向录取发布表中插入数据
     * @return
     */
    @GetMapping("/generateEms")
    public Result generateEms(@RequestParam Map<String, String> map){
        String startEms = map.get("startEms");
        if (startEms == null || "".equals(startEms)) {
            throw new ServiceException(Constants.CODE_400, "传参错误");
        }
        String sf = map.get("sf");

        if (sf == null || "".equals(sf)) {
            throw new ServiceException(Constants.CODE_400, "传参错误");
        }
        String num = map.get("num");
        if (num == null || "".equals(num)) {
            throw new ServiceException(Constants.CODE_400, "传参错误");
        }
        lqfbService.insertlqfbByCondition(map);
        return Result.success();
    }

    /**
     * 更新考生信息
     * @param ksxxUpateInfo 更新的考生信息
     * @return
     */
    @PostMapping("/updateStuInfo")
    public Result udpateStuInfo(@RequestBody KsxxUpateInfo ksxxUpateInfo) {
        if (ksxxUpateInfo.getKsh() == null || "".equals(ksxxUpateInfo.getKsh())) {
            throw new ServiceException(Constants.CODE_400, "传参错误");
        }
        ksxxService.updateStuInfo(ksxxUpateInfo);
        return Result.success();
    }

    /**
     * 查看历年录取分数线
     * @param current 当前页数
     * @param size 页面条数
     * @param query 查询条件
     * @return
     */
    @GetMapping("/liNianLuQu")
    public Result liNianLuQu(Integer current, Integer size, Query query){
        if (current == null || size == null) {
            throw new ServiceException(Constants.CODE_400, "分页参数错误");
        }
        if (query.getNf() == null || "".equals(query.getNf())) {
            query.setNf("2021");
        }
        if (query.getSf() == null || "".equals(query.getSf())) {
            throw new ServiceException(Constants.CODE_400, "参数错误");
        }
        IPage<Lqfsx> list = ksxxService.selectLqfsx(current, size, query);
        return  Result.success(list);
    }


    /**
     * 导出新生名册
     * @param response
     */
    @GetMapping("/ksxxZipDownload")
    public void ksxxZipDownload(HttpServletResponse response) throws IOException {
        ksxxService.ksxxZipDownload(response);
    }

    /**
     * 十个人数最多或最少的专业
     * @return
     */
    @GetMapping("/tenLargestAndLeastZy")
    public Result tenLargestAndLeastZy() {
        List<Map<String, Object>> zyMap = ksxxService.largestAndLeastZy();
        return Result.success(zyMap);
    }

    /**
     * 打包下载考生照片
     * @param response
     */
    @GetMapping("/kszpZipDownload")
    public void kszpZipDownload(HttpServletResponse response) {
        ksxxService.kszpZipDownload(response);
    }

    /**
     * 打包下载档案照片
     * @param response
     */
    @GetMapping("/dazpZipDownload")
    public void dazpZipDownload(HttpServletResponse response) {
        ksxxService.dazpZipDownload(response);
    }
}
