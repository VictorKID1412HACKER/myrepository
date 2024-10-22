package com.caicaigroup.hnuzslq_system.service;

import java.util.List;
import java.util.Map;

public interface TjfxService {
    /**
     * 按省份返回专业第一志愿报考率
     * @return
     */
    List<Map<String, Object>> firstEnrollRateBySf();

    /**
     * 按院系返回专业第一志愿录取率
     * @return
     */
    List<Map<String, Object>> firstAdmitRateByYx();

    /**
     * 按省份返回志愿报考率
     * @return
     */
    List<Map<String, Object>> enrollRateBySf();
}
