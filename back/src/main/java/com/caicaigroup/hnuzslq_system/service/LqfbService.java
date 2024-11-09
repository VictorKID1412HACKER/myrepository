package com.caicaigroup.hnuzslq_system.service;

import java.util.Map;

/**
 * @author 21g
 * @version 1.0
 * @className LqfbService
 * @since 1.0
 */
public interface LqfbService {

    /**
     * 根据条件插入录取分布记录
     * @param map
     */
    void insertlqfbByCondition(Map<String, String> map);

}
