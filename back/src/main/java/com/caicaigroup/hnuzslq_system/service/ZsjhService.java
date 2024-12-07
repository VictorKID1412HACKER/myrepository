package com.caicaigroup.hnuzslq_system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caicaigroup.hnuzslq_system.entity.Zsjh;
import com.caicaigroup.hnuzslq_system.entity.vo.Query;
import com.caicaigroup.hnuzslq_system.entity.vo.ZsjhDetail;

import com.caicaigroup.hnuzslq_system.entity.vo.ZsjhLineObj;
import java.util.List;

public interface ZsjhService {
    /**
     * 修改招生计划人数
     * @param zsjh 招生计划
     * @return
     */
    public Boolean updateRenShu(Zsjh zsjh);

    /**
     * 分页条件查询
     * @param current 当前页面
     * @param size 页面条数
     * @param query 查询条件
     * @return
     */
    public IPage<ZsjhDetail> search(int current, int size, Query query);

    /**
     * 按行查询招生计划
     * @param query 查询条件
     * @return
     */
    List<ZsjhLineObj> selectWithoutPagnition(Query query);
}
