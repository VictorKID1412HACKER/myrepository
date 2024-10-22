package com.caicaigroup.hnuzslq_system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caicaigroup.hnuzslq_system.entity.Fbqk;
import com.caicaigroup.hnuzslq_system.entity.vo.Bj;

import com.caicaigroup.hnuzslq_system.entity.vo.StuQuery;
import java.util.List;

public interface FbqkService {

    /**
     * 分批插入班级
     * @param list 列表
     */
    void insertByBatch(List<Fbqk> list);

    /**
     * 获得班级代号列表
     * @param zymc 专业名称
     * @return
     */
    List<String> getBjdhList(String zymc);

    /**
     * 查询分班情况
     * @param current 当前页数
     * @param size 页面条数
     * @param stuQuery 查询条件
     * @return
     */
    IPage<Bj> searchFb(Integer current, Integer size, StuQuery stuQuery);

    /**
     * 删除所有分班情况
     */
    void deleteAll();
}
