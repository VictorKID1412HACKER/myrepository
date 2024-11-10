package com.caicaigroup.hnuzslq_system.service.impl;

import com.caicaigroup.hnuzslq_system.mapper.TjfxMapper;
import com.caicaigroup.hnuzslq_system.service.TjfxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TjfxServiceImpl  implements TjfxService {

    @Autowired
    private TjfxMapper tjfxMapper;

    public List<Map<String, Object>> firstEnrollRateBySf() {
        List<Map<String, Object>> firstEnrollList = tjfxMapper.firstEnrollBySf(); // 第一志愿报考人数
        List<Map<String, Object>> zyAdmitList = tjfxMapper.zyAdmitNumBySf(); // 专业录取人数
        List<Map<String, Object>> listMap = new ArrayList<>();
        double firstZyEnrollNum = 0, zyAdmitNum = 0;
        for (Map<String, Object> map1 : zyAdmitList) {
            String sf1 = "", zy1 = "", sf2 = "", zy2 = "";
            boolean flag = true; // 该专业是否无人第一志愿报考
            for (String key1 : map1.keySet()) {
                if (key1.equals("sf")) {
                    sf1 = map1.get(key1).toString();
                } else if (key1.equals("zy")) {
                    zy1 = map1.get(key1).toString();
                } else if (key1.equals("zyAdmitNum")) {
                    zyAdmitNum = Double.parseDouble(map1.get(key1).toString());
                }
            }
            for (Map<String, Object> map2 : firstEnrollList) {
                for (String key2 : map2.keySet()) {
                    if (key2.equals("sf")) {
                        sf2 = map2.get(key2).toString();
                    } else if (key2.equals("zy")) {
                        zy2 = map2.get(key2).toString();
                    } else if (key2.equals("firstZyEnrollNum")) {
                        firstZyEnrollNum = Double.parseDouble(map2.get(key2).toString());
                    }
                }
                if (sf1.equals(sf2) && zy1.equals(zy2)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("省份名称", sf1);
                    map.put("专业名称", zy2);
                    map.put("第一志愿填报人数", firstZyEnrollNum);
                    map.put("录取人数", zyAdmitNum);
                    map.put("第一志愿报考率", (firstZyEnrollNum / zyAdmitNum) * 100);
                    listMap.add(map);
                    flag = false;
                    break;
                }
            }
            if (flag) { // 该专业无人第一志愿报考
                Map<String, Object> map = new HashMap<>();
                map.put("省份名称", sf1);
                map.put("专业名称", zy1);
                map.put("第一志愿填报人数", 0);
                map.put("录取人数", zyAdmitNum);
                map.put("第一志愿报考率", 0);
                listMap.add(map);
            }
        }
        return listMap;
    }

    public List<Map<String, Object>> firstAdmitRateByYx() {
        List<Map<String, Object>> firstEnrollList = tjfxMapper.firstEnrollByYx();
        List<Map<String, Object>> zyAdmitList = tjfxMapper.zyAdmitNumByYx();
        List<Map<String, Object>> listMap = new ArrayList<>();
        double firstZyEnrollNum = 0, yxAdmitNum = 0;
        for (Map<String, Object> map1 : zyAdmitList) {
            String yx1 = "", yx2 = "";
            boolean flag = true; // 该院系是否无人第一志愿报考
            for (String key1 : map1.keySet()) {
                if (key1.equals("yx")) {
                    yx1 = map1.get(key1).toString();
                } else if (key1.equals("yxAdmitNum")) {
                    yxAdmitNum = Double.parseDouble(map1.get(key1).toString());
                }
            }
            for (Map<String, Object> map2 : firstEnrollList) {
                for (String key2 : map2.keySet()) {
                    if (key2.equals("yx")) {
                        yx2 = map2.get(key2).toString();
                    } else if (key2.equals("firstZyEnrollNum")) {
                        firstZyEnrollNum = Double.parseDouble(map2.get(key2).toString());
                    }
                }
                if (yx1.equals(yx2)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("院系名称", yx1);
                    map.put("第一志愿录取人数", firstZyEnrollNum);
                    map.put("总录取人数", yxAdmitNum);
                    map.put("第一志愿录取率", (firstZyEnrollNum / yxAdmitNum) * 100);
                    listMap.add(map);
                    flag = false;
                    break;
                }
            }
            if (flag) { // 该院系无人第一志愿报考
                Map<String, Object> map = new HashMap<>();
                map.put("院系名称", yx1);
                map.put("第一志愿录取人数", 0);
                map.put("录取人数", yxAdmitNum);
                map.put("第一志愿录取率", 0);
                listMap.add(map);
            }
        }
        return listMap;
    }

    public List<Map<String, Object>> enrollRateBySf(){
        List<Map<String, Object>> enrollList = tjfxMapper.enrollBySf();
        System.out.println(enrollList);
        List<Map<String, Object>> zyAdmitList = tjfxMapper.zyAdmitNumBySf();
        List<Map<String, Object>> listMap = new ArrayList<>();
        double fillInNum = 0, zyAdmitNum = 0;
        for (Map<String, Object> map1 : zyAdmitList) {
            String sf1 = "", zy1 = "", sf2 = "", zy2 = "";
            boolean flag = true; // 该专业是否无人报考
            for (String key1 : map1.keySet()) {
                if (key1.equals("sf")) {
                    sf1 = map1.get(key1).toString();
                } else if (key1.equals("zy")) {
                    zy1 = map1.get(key1).toString();
                } else if (key1.equals("zyAdmitNum")) {
                    zyAdmitNum = Double.parseDouble(map1.get(key1).toString());
                }
            }
            for (Map<String, Object> map2 : enrollList) {
                for (String key2 : map2.keySet()) {
                    if (key2.equals("sf")) {
                        sf2 = map2.get(key2).toString();
                    } else if (key2.equals("zy")) {
                        zy2 = map2.get(key2).toString();
                    } else if (key2.equals("fillInNum")) {
                        fillInNum = Double.parseDouble(map2.get(key2).toString());
                    }
                }
                if (sf1.equals(sf2) && zy1.equals(zy2)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("省份名称", sf1);
                    map.put("专业名称", zy1);
                    map.put("志愿填报人数", fillInNum);
                    map.put("录取人数", zyAdmitNum);
                    map.put("志愿报考率", (fillInNum / zyAdmitNum) * 100);
                    listMap.add(map);
                    flag = false;
                    break;
                }
            }
            if (flag) { // 该专业无人第一志愿报考
                Map<String, Object> map = new HashMap<>();
                map.put("省份名称", sf1);
                map.put("专业名称", zy1);
                map.put("志愿填报人数", 0);
                map.put("录取人数", zyAdmitNum);
                map.put("志愿报考率", 0);
                listMap.add(map);
            }
        }
        return listMap;
    }


}
