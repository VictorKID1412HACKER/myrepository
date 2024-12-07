package com.caicaigroup.hnuzslq_system.mapper;

import com.caicaigroup.hnuzslq_system.entity.Ksxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用于临时表
 * 生成表并批量插入数据库 表格mapper
 */
@Mapper
public interface TableMapper {

    /**
     * 创建数据库表
     * @param createTableSql 建表语句
     */
    void createTable(String createTableSql);

    /**
     * 批量插入
     * @param tableName 表名
     * @param head 表头
     * @param list 数据
     */
    void insertListData(@Param("tableName") String tableName,
                        @Param("head") String head,
                        @Param("list") List<String> list);

    /**
     * 删除表
     * @param tableName 表名
     */
    void dropTempTalbe(@Param("tableName") String tableName)throws Exception;

    /**
     * 清空表
     * @param tableName
     */
    void truncateTable(@Param("tableName") String tableName);


    /**
     * 甘肃代码转换
     * @param tableName 表名
     * @return
     */
    int updateGsTdd(@Param("tableName") String tableName)throws Exception;

    /**
     * 甘肃tdd上载ksxx
     * @param tableName
     * @param sf 省份
     * @param jhlb 计划类别
     * @param kl 科类
     * @return
     */
    int gsTddInsertKsxx(@Param("tableName") String tableName,
                        @Param("sf") String sf,
                        @Param("jhlb") String jhlb,
                        @Param("kl") String kl)
            throws Exception;

    /**
     * 甘肃tdd上载gkcjx
     * @param tableName
     * @return
     */
    int gsTddInsertGkcjx(@Param("tableName") String tableName)throws Exception;

    /**
     * 甘肃tdd上载Zyzy
     * @param tableName
     * @return
     */
    int gsTddInsertZyzy(@Param("tableName") String tableName)throws Exception;

    /**
     * 湖南tdd代码转换
     * @param tableName
     * @return
     * @throws Exception
     */
    int updateHnTdd(@Param("tableName") String tableName)throws Exception;

    /**
     * 湖南专业志愿转换 zykmx
     * @param tableName
     * @return
     * @throws Exception
     */
    int updateHnZykmx(@Param("tableName") String tableName)throws Exception;

    /**
     * 湖南bmk上载ksxx
     * @param tableName
     * @return
     * @throws Exception
     */
    int hnBmkInsertKsxx(@Param("tableName") String tableName) throws Exception;

    /**
     * 湖南tdd上载ksxx
     * @param tableName
     * @param sf 省份
     * @param jhlb 计划类别
     * @param kl 科类
     * @return
     * @throws Exception
     */
    int hnTddInsertKsxx(@Param("tableName") String tableName,
                        @Param("sf") String sf,
                        @Param("jhlb") String jhlb,
                        @Param("kl") String kl)
            throws Exception;

    /**
     * 湖南bmk上载gkcjx
     * @param tableName
     * @return
     * @throws Exception
     */
    int hnBmkInsertGkcjx(@Param("tableName") String tableName)throws Exception;

    /**
     * 湖南zykmx上载zyzy
     * @param tableName
     * @return
     * @throws Exception
     */
    int hnZykmxInsertZyzy(@Param("tableName") String tableName)throws Exception;

    /**
     * 上海tdd代码转换
     * @param tableName
     * @return
     * @throws Exception
     */
    int updateShTdd(@Param("tableName") String tableName)throws Exception;

    /**
     * 上海专业志愿转换 zykmx
     * @param tableName
     * @return
     * @throws Exception
     */
    int updateShZykmx(@Param("tableName") String tableName)throws Exception;

    /**
     * 上海bmk上载ksxx
     * @param tableName
     * @return
     * @throws Exception
     */
    int shBmkInsertKsxx(@Param("tableName") String tableName) throws Exception;

    /**
     * 上海tdd上载ksxx
     * @param tableName
     * @param sf 省份
     * @param jhlb 计划类别
     * @param kl 科类
     * @return
     * @throws Exception
     */
    int shTddInsertKsxx(@Param("tableName") String tableName,
                        @Param("sf") String sf,
                        @Param("jhlb") String jhlb,
                        @Param("kl") String kl)
            throws Exception;

    /**
     * 上海bmk上载gkcjx
     * @param tableName
     * @return
     * @throws Exception
     */
    int shBmkInsertGkcjx(@Param("tableName") String tableName)throws Exception;

    /**
     * 上海zykmx上载zyzy
     * @param tableName
     * @return
     * @throws Exception
     */
    int shZykmxInsertZyzy(@Param("tableName") String tableName)throws Exception;

    /**
     * 获取ksxx 中文
     * @param sf 省份
     * @param jhlb 计划类别
     * @param kl 科类
     * @return ksxx集合
     */
    List<Ksxx> CheckKsxxCN(@Param("sf") String sf,
                           @Param("jhlb") String jhlb,
                           @Param("kl") String kl);
}
