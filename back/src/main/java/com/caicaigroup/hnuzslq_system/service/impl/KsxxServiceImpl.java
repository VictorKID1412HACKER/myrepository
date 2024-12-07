package com.caicaigroup.hnuzslq_system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caicaigroup.hnuzslq_system.common.Constants;
import com.caicaigroup.hnuzslq_system.entity.Fbpz;
import com.caicaigroup.hnuzslq_system.entity.Fbqk;
import com.caicaigroup.hnuzslq_system.entity.Ksxx;
import com.caicaigroup.hnuzslq_system.entity.KsxxWithBLOBs;
import com.caicaigroup.hnuzslq_system.entity.Zd;
import com.caicaigroup.hnuzslq_system.entity.Zydm;
import com.caicaigroup.hnuzslq_system.entity.vo.Bj;
import com.caicaigroup.hnuzslq_system.entity.vo.KsFb;
import com.caicaigroup.hnuzslq_system.entity.vo.KsxxUpateInfo;
import com.caicaigroup.hnuzslq_system.entity.vo.Lqfsx;
import com.caicaigroup.hnuzslq_system.entity.vo.Query;
import com.caicaigroup.hnuzslq_system.entity.vo.StuQuery;
import com.caicaigroup.hnuzslq_system.exception.ServiceException;
import com.caicaigroup.hnuzslq_system.mapper.FbpzMapper;
import com.caicaigroup.hnuzslq_system.mapper.FbqkMapper;
import com.caicaigroup.hnuzslq_system.mapper.KsxxMapper;
import com.caicaigroup.hnuzslq_system.mapper.TableMapper;
import com.caicaigroup.hnuzslq_system.mapper.ZdMapper;
import com.caicaigroup.hnuzslq_system.mapper.ZydmMapper;
import com.caicaigroup.hnuzslq_system.service.KsxxService;
import com.caicaigroup.hnuzslq_system.utils.CSVUtils;
import com.caicaigroup.hnuzslq_system.utils.DBFUtil;
import com.caicaigroup.hnuzslq_system.utils.FileUtil;
import com.caicaigroup.hnuzslq_system.utils.JPGUtil;
import com.caicaigroup.hnuzslq_system.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.Collections;

import static com.caicaigroup.hnuzslq_system.utils.FileUtil.getExportExcelBytes;

@Transactional
@Service
@Component
public class KsxxServiceImpl extends ServiceImpl<KsxxMapper, Ksxx> implements KsxxService {

    @Autowired
    private KsxxMapper ksxxMapper;

    @Autowired
    private FbpzMapper fbpzMapper;

    @Autowired
    private ZdMapper zdMapper;

    @Autowired
    private ZydmMapper zydmMapper;

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private FbqkMapper fbqkMapper;

    //因为很少，直接写死
    private String[] xbList = {"1", "2"}; // 性别代码
    private String[] sfList = {"31", "43", "62"}; // 省份代码

    @Override
    public boolean uploadKsxx(MultipartFile[] files, String sf) {
        String id =TokenUtils.getCurrentUser().getId().toString();
        FileUtil.uploadFiles(files,id); // 存入服务器中
        boolean flag1=DBFUtil.handleDbfFile(id); // 处理成csv文件
        boolean flag2=CSVUtils.handleCsvFile(id); // 插入数据库中
        // 上载至数据库后删除服务器的临时文件
        boolean flag3=FileUtil.deleteTempFile(id);
        if(flag1&&flag2&&flag3){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean gsTddBmkToKsxx(String sf, String jhlb, String kl) {
        String id =TokenUtils.getCurrentUser().getId().toString();
        String tableName="t_tdd"+"_"+id;
        try {
            tableMapper.updateGsTdd(tableName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"审核失败，请检查是否上传正确文件");
        }
        try {
            tableMapper.gsTddInsertKsxx(tableName,sf,jhlb,kl);
            tableMapper.gsTddInsertGkcjx(tableName);
            tableMapper.gsTddInsertZyzy(tableName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_501,"该部分考生数据已入库");
        }
        return true;
    }

    @Override
    public boolean hnTddBmkToKsxx(String sf, String jhlb, String kl) {
        String id =TokenUtils.getCurrentUser().getId().toString();
        String tddTable="T_TDD_"+id;
        String bmkTable="t_bmk_"+id;
        String zykmxTable="t_zykmx_"+id;

        try {
            tableMapper.updateHnTdd(tddTable);
            tableMapper.updateHnZykmx(zykmxTable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"审核失败，请检查是否上传正确文件，请重新上传");
        }
        try {
            tableMapper.hnBmkInsertKsxx(bmkTable);
            tableMapper.hnTddInsertKsxx(tddTable,sf,jhlb,kl);
            tableMapper.hnBmkInsertGkcjx(bmkTable);
            tableMapper.hnZykmxInsertZyzy(zykmxTable);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                tableMapper.dropTempTalbe(tddTable);
                tableMapper.dropTempTalbe(bmkTable);
                tableMapper.dropTempTalbe(zykmxTable);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ServiceException(Constants.CODE_500,"数据库错误");
            }
            throw new ServiceException(Constants.CODE_501,"该部分考生数据已入库");
        }
        try {
            tableMapper.dropTempTalbe(tddTable);
            tableMapper.dropTempTalbe(bmkTable);
            tableMapper.dropTempTalbe(zykmxTable);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"数据库错误");
        }
        return true;
    }

    @Override
    public boolean shTddBmkToKsxx(String sf, String jhlb, String kl) {
        String id =TokenUtils.getCurrentUser().getId().toString();
        String tddTable="T_TDD"+"_"+id;
        String bmkTable="t_bmk"+"_"+id;
        String zykmxTable="t_zykmx_"+id;

        try {
            tableMapper.updateShTdd(tddTable);
            tableMapper.updateShZykmx(zykmxTable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"审核失败，请检查是否上传正确文件，请重新上传");
        }
        try {
            tableMapper.shBmkInsertKsxx(bmkTable);
            tableMapper.shTddInsertKsxx(tddTable,sf,jhlb,kl);
            tableMapper.shBmkInsertGkcjx(bmkTable);
            tableMapper.shZykmxInsertZyzy(zykmxTable);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                tableMapper.dropTempTalbe(tddTable);
                tableMapper.dropTempTalbe(bmkTable);
                tableMapper.dropTempTalbe(zykmxTable);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ServiceException(Constants.CODE_500,"数据库错误");
            }
            throw new ServiceException(Constants.CODE_501,"该部分考生数据已入库");
        }
        try {
            tableMapper.dropTempTalbe(tddTable);
            tableMapper.dropTempTalbe(bmkTable);
            tableMapper.dropTempTalbe(zykmxTable);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"数据库错误");
        }
        return true;
    }

    @Override
    public List<Ksxx> CheckKsxxCN(String sf, String jhlb, String kl) {
        List<Ksxx> ksxxList=tableMapper.CheckKsxxCN(sf,jhlb,kl);
        return ksxxList;
    }

    @Override
    public Integer uploadPhoto(MultipartFile[] files,String zplx) {
        Integer amount=0;
        String id =TokenUtils.getCurrentUser().getId().toString();
        FileUtil.uploadFiles(files,id); // 上传照片
        // 处理照片 获取考生号和照片二进制串集合
        Map<String,byte[]> map=JPGUtil.handleImageToBlob(id);
        for (Map.Entry<String,byte[]> entry : map.entrySet()) {
            String ksh = entry.getKey();
            byte[] imageBytes=entry.getValue();
            System.out.println("ksh:"+ksh+" blob:"+imageBytes);
            KsxxWithBLOBs ksxxWithBLOBs = new KsxxWithBLOBs();
            ksxxWithBLOBs.setKsh(ksh);
            if(zplx.equals("考生照片")){
                ksxxWithBLOBs.setKszp(imageBytes);
            }else if(zplx.equals("档案照片1")){
                ksxxWithBLOBs.setDazp1(imageBytes);
            }else if(zplx.equals("档案照片2")){
                ksxxWithBLOBs.setDazp2(imageBytes);
            }else if(zplx.equals("档案照片3")){
                ksxxWithBLOBs.setDazp3(imageBytes);
            }else {
                throw new ServiceException(Constants.CODE_400,"传参错误");
            }
            amount+=ksxxMapper.updateKsxxZp(ksxxWithBLOBs);
        }
        FileUtil.deleteJpgTempFile(id); // 删除照片文件夹缓存文件
        return amount;
    }

    @Override
    public Integer getSfKsxxAmount(String sf) {
        return ksxxMapper.selectBySfdm(sf);
    }

    @Override
    public byte[] getKszp(String ksh) {
        byte[] imageBlob=null;
        KsxxWithBLOBs ksxxWithBLOBs= null;
        try {
            imageBlob = ksxxMapper.selectByKshKszp(ksh);
            System.out.println("ser:"+imageBlob);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_600,"该考生照片未入库！");
        }
        imageBlob=ksxxWithBLOBs.getKszp();
        return imageBlob;
    }


    @Override
    public IPage<KsFb> search(int current, int size, StuQuery stuQuery) {
        Page<KsFb> page = new Page<>(current, size);
        IPage<KsFb> ksFbIPage = ksxxMapper.selectDetail(page, stuQuery);
        return ksFbIPage;
    }

    @Override
    public void updateBjdh(List<String> kshList, String bjdh) {
        for (String ksh : kshList) {
            KsxxWithBLOBs ksxxWithBLOBs = ksxxMapper.selectByPrimaryKey(ksh);
            ksxxWithBLOBs.setBjdh(bjdh);
            ksxxMapper.updateByPrimaryKey(ksxxWithBLOBs);
        }
    }

    @Override
    public Map<String, Object> selectBjList() {

        List<Fbpz> fbpzList = fbpzMapper.selectList(null);
        Map<String, Object> map = new HashMap<>();
        List<Fbqk> fbqkList = new ArrayList<>();
        List<Bj> bjList = new ArrayList<>();
        for (Fbpz fbpz : fbpzList) {
            Map<String, Object> stringObjectMap = fbAutoByZy(fbpz, map);
            List<Fbqk> fbqks = (List<Fbqk>) stringObjectMap.get("fbqks");
            List<Bj> bjs = (List<Bj>) stringObjectMap.get("bjs");
            fbqkList.addAll(fbqks);
            bjList.addAll(bjs);

        }
        map.put("fbqks", fbqkList);
        map.put("bjs", bjList);
        return map;
    }

    @Override
    public void setfb(Fbqk fbqk) {
        List<String> kshList = fbqk.getKshList();
        if (kshList == null) {
            return;
        }
        for (int i = 0; i < kshList.size(); i++) {
            String ksh = kshList.get(i);
            KsxxWithBLOBs ksxxWithBLOBs = new KsxxWithBLOBs();
            ksxxWithBLOBs.setKsh(ksh);
            ksxxWithBLOBs.setBjdh(fbqk.getBjdh());
            ksxxWithBLOBs.setXh(fbqk.getBjdmqz() + fbqk.getBjdh() + String.format("%02d", i + 1));
            ksxxMapper.updateBjdhAndXh(ksxxWithBLOBs);
        }
    }

    @Override
    public void updateKsxx(KsxxWithBLOBs ksxx) {
        QueryWrapper<Ksxx> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ksh", ksxx.getKsh());
        update(ksxx, queryWrapper);
    }

    @Override
    public void updateStuInfo(KsxxUpateInfo ksxxUpateInfo) {
        QueryWrapper<Ksxx> ksxxQueryWrapper = new QueryWrapper<>();
        ksxxQueryWrapper.eq("ksh", ksxxUpateInfo.getKsh());
        Ksxx ksxx = ksxxMapper.selectOne(ksxxQueryWrapper);
        System.out.println(ksxxUpateInfo);

        if (ksxxUpateInfo.getJh() != null && !"".equals(ksxxUpateInfo.getJh())) {
            Map<String, String> map = new HashMap<>();
            map.put("dmmc", ksxxUpateInfo.getJh());
            map.put("dmlb", "jhlbdm");
            map.put("bs", "GB");
            String lbdm = zdMapper.selectDmByDmmcAndLbBs(map);
            ksxx.setSfdm(lbdm);
            ksxxMapper.update(ksxx, ksxxQueryWrapper);
        }

        if (ksxxUpateInfo.getKl() != null && !"".equals(ksxxUpateInfo.getKl())) {
            Map<String, String> map = new HashMap<>();
            map.put("dmmc", ksxxUpateInfo.getKl());
            map.put("dmlb", "kldm");
            map.put("bs", "HD");
            String kldm = zdMapper.selectDmByDmmcAndLbBs(map);
            ksxx.setKldm(kldm);
            ksxxMapper.update(ksxx, ksxxQueryWrapper);
        }

        if (ksxxUpateInfo.getZy() != null && !"".equals(ksxxUpateInfo.getZy())) {
            Map<String, String> map = new HashMap<>();
            map.put("zymc", ksxxUpateInfo.getZy());
            map.put("year", "2021");
            String zydh = zydmMapper.selectZydhByZymcAndYear(map);
            ksxx.setLqzydm(zydh);
            ksxxMapper.update(ksxx, ksxxQueryWrapper);
        }
    }

    @Override
    public IPage<Lqfsx> selectLqfsx(Integer current, Integer size, Query query) {
        Page<Lqfsx> page = new Page<>(current, size);
        IPage<Lqfsx> LqfsxIPage = ksxxMapper.selectLqfsx(page, query);
        return LqfsxIPage;
    }

    @Override
    public Ksxx getKsxxByKsh(String ksh) {
        Ksxx ksxx=ksxxMapper.selectByKsh(ksh);
        return ksxx;
    }

    /**
     * 对传入的分班配置更新分班情况与班级
     * @param fbpz 分班配置
     * @param map  分班情况和班级列表
     * @return
     */
    private Map<String, Object> fbAutoByZy(Fbpz fbpz, Map<String, Object> map) {

        int bjNum = fbpz.getBjsl();
        List<Fbqk> fbqkList = initFbqkByFbpz(fbpz);

        for (int xbIndex = 0; xbIndex < xbList.length; xbIndex++) {
            for (int sfIndex = 0; sfIndex < sfList.length; sfIndex++) {
                String xb = xbList[xbIndex];
                String sf = sfList[sfIndex];
                QueryWrapper<Ksxx> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("xbdm", xb);
                queryWrapper.eq("lqzydm", fbpz.getZydh());
                queryWrapper.likeRight("lqsj", "2021");
                queryWrapper.eq("sfdm", sf);
                List<Ksxx> ksxxList = ksxxMapper.selectList(queryWrapper);
                fbDetail(ksxxList, bjNum, fbqkList);
            }
        }
        List<Bj> bjList = generateBjByFbqk(fbqkList);
        map.put("fbqks", fbqkList);
        map.put("bjs", bjList);
        return map;
    }

    /**
     * 根据分班情况列表生成班级情况
     * @param fbqkList
     * @return
     */
    private List<Bj> generateBjByFbqk(List<Fbqk> fbqkList) {
        List<Bj> bjList = new ArrayList<>();
        for (int i = 0; i < fbqkList.size(); i++) {
            Fbqk fbqk = fbqkList.get(i);
            Bj bj = new Bj();
            BeanUtil.copyProperties(fbqk, bj, true);

            QueryWrapper<Zd> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("dmlb", "yxdm");
            queryWrapper1.eq("dm", fbqk.getYxdm());
            String yx = zdMapper.selectOne(queryWrapper1).getDmmc();

            QueryWrapper<Zydm> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("year", "2021");
            queryWrapper2.eq("zydh", fbqk.getZydh());
            String zy = zydmMapper.selectOne(queryWrapper2).getZymc();

            bj.setYx(yx);
            bj.setZy(zy);

            bjList.add(bj);
        }
        return bjList;
    }

    /**
     * 根据分班配置初始创建分班情况
     * @param fbpz
     * @return
     */
    private List<Fbqk> initFbqkByFbpz(Fbpz fbpz) {
        int num = fbpz.getBjsl();
        List<Fbqk> fbqkList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Fbqk fbqk = new Fbqk();
            fbqk.setZydh(fbpz.getZydh());
            fbqk.setYxdm(fbpz.getYxdm());
            fbqk.setBjdh(String.format("%02d", i + 1));
            fbqk.setBjmc(fbpz.getBjmcqz() + fbqk.getBjdh() + "班");
            fbqk.setBjdmqz(fbpz.getBjdmqz());
            fbqk.setXssl(0);
            fbqk.setMnum(0);
            fbqk.setFnum(0);
            fbqkList.add(fbqk);
        }
        return fbqkList;
    }

    /**
     * 具体实现分班到人
     * @param ksxxList
     * @param bjNum
     * @param fbqkList
     */
    private void fbDetail(List<Ksxx> ksxxList, int bjNum, List<Fbqk> fbqkList) {
        int j = 0;
        for (int i = 0; i < ksxxList.size(); i++) {
            if (fbqkList.get(j).getKshList() == null) {
                fbqkList.get(j).setKshList(new ArrayList<>());
            }
            fbqkList.get(j).getKshList().add(ksxxList.get(i).getKsh());
            fbqkList.get(j).setXssl(fbqkList.get(j).getXssl() + 1);
            if ("1".equals(ksxxList.get(i).getXbdm())) {
                fbqkList.get(j).setMnum(fbqkList.get(j).getMnum() + 1);
            } else {
                fbqkList.get(j).setFnum(fbqkList.get(j).getFnum() + 1);
            }
            j = (j + 1) % bjNum;
        }
    }

    /**
     * 全部考生信息 按录取专业和考生号排序
     * @return
     */
    public List<Ksxx> ksxxInfoCN() {
        return ksxxMapper.selectksxxCN();
    }


    /**
     * 下载考生信息zip
     * @return
     */
    public void ksxxZipDownload(HttpServletResponse response) throws IOException {
        Map<String, byte[]> ksxxMap = new HashMap<>();

        List<Ksxx> ksxxList = ksxxInfoCN();

        List<Ksxx> tmpList = new ArrayList<>();
        String lqzydm = ksxxList.get(0).getLqzydm();

        for (Ksxx ksxx : ksxxList) {
            if (ksxx.getLqzydm().equals(lqzydm)) {
                tmpList.add(ksxx);
            } else {
                byte[] exportKsxxExcelBytes = getExportExcelBytes(tmpList, null, "sheet1",
                        Ksxx.class, "filename");
                ksxxMap.put(lqzydm + ".xls", exportKsxxExcelBytes);
                lqzydm = ksxx.getLqzydm();
                tmpList.clear();
                tmpList.add(ksxx);
            }
        } // 如果用截取 截取出来的子list只是原list的视图 在转换成byte数组后tmplist.size()==0，相当于修改了原list，报错
        byte[] exportKsxxExcelBytes = getExportExcelBytes(tmpList, null, "sheet1",
                Ksxx.class, "filename");
        ksxxMap.put(lqzydm + ".xls", exportKsxxExcelBytes);

        FileUtil.zipDownload(ksxxMap, "ksxx.zip", response);
    }


    public List<Map<String, Object>> largestAndLeastZy() {
        List<Map<String, Object>> zyList = ksxxMapper.selectCountZyCN();
            // 排序
            Collections.sort(zyList, new Comparator<Map<String, Object>>(){

                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    int count1 = Integer.parseInt(String.valueOf(o1.get("count")));
                    int count2 = Integer.parseInt(String.valueOf(o2.get("count")));
                    if(count1 > count2 ) return 1;
                    else if(count1 == count2) return 0;
                    else return -1;
                }

            });
            // 取前五个和后五个专业
            List<Map<String, Object>> list = new ArrayList<>();
            int i = 0;
            int size = zyList.size();
            for(Map<String, Object> map : zyList){
                if (i < 5 || i >= size - 5) {
                    list.add(map);
                }
                i++;
            }
        return list;
    }

    public void kszpZipDownload(HttpServletResponse response){
        Map<String, byte[]> zpMap = new HashMap<>();

        // 获取考生照片
        List<Map<String, Object>> kszpList = ksxxMapper.selectKsZp();
        // 对每一个考生 生成照片路径 并将照片转换为字节流
        for(Map<String, Object> map : kszpList){
            String photoPath = generatePhotoPath(map);
            if (map.get("kszp") != null) {
                zpMap.put(photoPath + ".jpg", (byte[])map.get("kszp"));
            }
        }
        FileUtil.zipDownload(zpMap,"kszp.zip", response);
    }

    public String generatePhotoPath(Map map) {
            String yx = map.get("yxmc").toString();
            String xh = map.get("xh").toString();
            String zymc = map.get("zymc").toString();
            String bjdh = map.get("bjdh").toString();
            String path = yx + '/' + zymc + '/' + bjdh + '/' + xh;
            return path;
    }

    public void dazpZipDownload(HttpServletResponse response) {
        Map<String, byte[]> zpMap = new HashMap<>();

        // 获取档案照片
        List<Map<String, Object>> dazpList = ksxxMapper.selectdazp();
        // 对每一个考生 生成照片路径 并将照片转换为字节流
        for(Map<String, Object> map : dazpList){
            String photoPath = generatePhotoPath(map);
            if (map.get("dazp1") != null) {
                zpMap.put(photoPath + "/dazp1.jpg", (byte[])map.get("dazp1"));
            }
            if (map.get("dazp2") != null) {
                zpMap.put(photoPath + "/dazp2.jpg", (byte[])map.get("dazp2"));
            }
            if (map.get("dazp3") != null) {
                zpMap.put(photoPath + "/dazp3.jpg", (byte[])map.get("dazp3"));
            }
        }
        FileUtil.zipDownload(zpMap,"dazp.zip",response);
    }
}
