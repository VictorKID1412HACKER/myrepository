package com.caicaigroup.hnuzslq_system.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TjfxMapper {

    /**
     * 按省份返回专业第一志愿填报人数
     * @return
     */
    @MapKey("key")
    List<Map<String, Object>> firstEnrollBySf();

    /**
     * 按省份返回院系录取人数
     * @return
     */
    @MapKey("key")
    List<Map<String, Object>> zyAdmitNumBySf();

    /**
     * 按院系返回专业第一志愿填报人数
     * @return
     */
    @MapKey("key")
    List<Map<String, Object>> firstEnrollByYx();

    /**
     * 按院系返回专业录取人数
     * @return
     */
    @MapKey("key")
    List<Map<String, Object>> zyAdmitNumByYx();

    /**
     * 按省份返回志愿填报人数
     * @return
     */
    @MapKey("key")
    List<Map<String, Object>> enrollBySf();
}
