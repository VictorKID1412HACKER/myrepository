package com.caicaigroup.hnuzslq_system.utils;

import com.caicaigroup.hnuzslq_system.common.Constants;
import com.caicaigroup.hnuzslq_system.exception.ServiceException;
import com.caicaigroup.hnuzslq_system.mapper.TableMapper;
import jdk.nashorn.internal.runtime.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理CSV文件，插入到数据库中
 */
@Slf4j
@Component
public class CSVUtils {

    // 调用mapper
    @Autowired
    private TableMapper tableMapper;

    public static CSVUtils csvUtils;

    // 创建一个初始化方法，贴上@PostConstruct 标签，用于注入bean
    @PostConstruct
    public void init(){
        csvUtils = this ;
        csvUtils.tableMapper = this.tableMapper;
    }

    private static String uploadCsvPath; // 存储csv文件的路径

    @Value("${file.upload.url.csv}")
    public void setUploadCsvPath(String uploadCsvPath) {
        CSVUtils.uploadCsvPath = uploadCsvPath;
    }

    /**
     * 遍历csv文件夹，读取文件插入数据库中
     * @param handlePersonName 当前处理人名称 当前登陆用户id
    */
    public static boolean handleCsvFile(String handlePersonName){
        // 从csv目录里获取csv文件
        String path=uploadCsvPath+"_"+handlePersonName;
        File f=new File(path);
        File[] files=f.listFiles();
        boolean flag=false;
        for(File file:files) {
            flag=false;
            flag=csvUtils.insertData(file,handlePersonName);
        }
        if(flag==true){
            System.out.println("handle CsvFile success");
            return true;
        }else {
            System.out.println("handle CsvFile error");
            return false;
        }
    }

    /**
     * 批量插入csv文件中的数据至数据库中
     * @param file 文件
     */
    public boolean insertData(File file,String handlePersonName) {

        String head = null;
        List<String> list = null;
        BufferedReader reader = null;

        String fileRealName=file.getName();
        String fileName=fileRealName.substring(0, fileRealName.lastIndexOf("."));
        String tableName=fileName+"_"+handlePersonName;

        //读取第一行的标题信息
        try {
            reader = new BufferedReader(new FileReader(file));
            head = reader.readLine();

            if(head == null){
                throw new ServiceException(Constants.CODE_500, "系统错误：该csv文件表头为空");
            } else {
                // 根据该表头创建数据库表
                createTableSql(head,tableName);
            }

            // 读取csv文件内容 批量插入
            while ((list = readCSV(reader)).size() != 0){
                csvUtils.tableMapper.insertListData(tableName,head,list);
                log.info("文件{}导入中--- +{}行",file.getPath(),list.size());
            }
        } catch (IOException e) {
            log.info("***文件{}导入数据库出现异常***",file.getPath());
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 读取csv中的数据并转换成mysql可以插入的格式到list中（不读取表头，从第二行开始获取数据）
     * @param reader 字符缓冲流
     * @return java.util.List 读取到的数据（一次读取1000行）
     */
    public List readCSV(BufferedReader reader){
        try {
            ArrayList<String> list = new ArrayList<String>();
            String line = null;
            for(int i=0; i < 1000; i++) {
                line = reader.readLine();

                if(line == null){
                    break;
                }
                // 替换成mysql可以插入的格式
                line = "'"+line.replaceAll(",","','")+"'";
                line=line.replaceAll("\'\'","NULL");
                // 测试输出
                System.out.println("read:"+line);
                list.add(line);
            }
            return list;
        } catch (Exception e) {
            Context.ThrowErrorManager log = null;
            log.error("文件:{}读取信息出错");
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把csv表头转换成mysql建表语句，并创建数据库表
     * （后续优化tip：添加异常处理）
     * @param csvHead csv表头
     * @param tableName
     */
    public void createTableSql(String csvHead,String tableName){
        // 如果存在该表则删除
        try {
            csvUtils.tableMapper.dropTempTalbe(tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 构建建表sql语句
        String [] col = csvHead.split(",");
        String createTableSql="CREATE TABLE "+tableName+"(\n`id` int(11) NOT NULL AUTO_INCREMENT,";
        for(String ss:col){
            createTableSql+=ss+" TEXT DEFAULT NULL,\n";
        }
        createTableSql += "PRIMARY KEY (`id`)\n) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";

        // 创建数据库表
        csvUtils.tableMapper.createTable(createTableSql);
    }

}
