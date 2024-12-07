package com.caicaigroup.hnuzslq_system.service;

import com.caicaigroup.hnuzslq_system.entity.vo.ZdKeyAndVal;

import java.util.List;
import java.util.Map;

/**
 * @author 21g
 * @version 1.0
 * @className ZydmService
 * @since 1.0
 */
public interface ZydmService {

    /**
     * 根据院系查找专业列表
     * @param map 专业代码与名称
     * @return
     */
    List<ZdKeyAndVal> selectZyByYx(Map<String, String> map);

}
