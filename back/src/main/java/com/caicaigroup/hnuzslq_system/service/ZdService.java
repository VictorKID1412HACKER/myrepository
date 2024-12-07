package com.caicaigroup.hnuzslq_system.service;

import com.caicaigroup.hnuzslq_system.entity.vo.ZdKeyAndVal;

import java.util.List;

/**
 * @author 21g
 * @version 1.0
 * @className ZdService
 * @since 1.0
 */
public interface ZdService {

    /**
     * 根据传入的要求返回字典列表
     * @param type 需要获得的类型
     * @return
     */
    List<ZdKeyAndVal> selectPairByType(String type);

}
