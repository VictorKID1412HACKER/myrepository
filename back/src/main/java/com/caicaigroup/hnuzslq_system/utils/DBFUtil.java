package com.caicaigroup.hnuzslq_system.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 处理dbf文件
 */
@Component
public class DBFUtil {

    private static DBFUtil dbfUtil;

    @PostConstruct
    public void init(){
        dbfUtil = this ;
    }

    private static String DbfPath;

    @Value("${file.upload.url.dbf}")
    public void setDbfPath(String DbfPath) {
        DBFUtil.DbfPath = DbfPath;
    }

    private static String CsvPath;

    @Value("${file.upload.url.csv}")
    public void setCsvfPath(String CsvPath) {
        DBFUtil.CsvPath = CsvPath;
    }

    private static String pyPath;

    @Value("${file.upload.url.py_script}")
    public void setPyfPath(String pyPath) {
        DBFUtil.pyPath = pyPath;
    }

    /**
     * 遍历dbf文件夹，转换成csv文件
     *   如果不存在csv路径则创建
     * @param userID 当前登陆用户id
     */
    /**
     * 遍历dbf文件夹，转换成csv文件
     *   如果不存在csv路径则创建
     * @param userID 当前登陆用户id
     * @return 1 成功 0 失败
     */
    public static boolean handleDbfFile(String userID){
        String csvPath=CsvPath+"_"+userID;
        FileUtil.createDir(csvPath);
        String dbfPath=DbfPath+"_"+userID;
        File f=new File(dbfPath);
        File [] files=f.listFiles();
        boolean flag=false;
        for(File file:files) {
            String filePath=file.getPath();
            flag=false;
            try {
                flag=dbfUtil.executePython(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(flag==true){
            System.out.println("handle DbfFile success");
            return true;
        }else {
            System.out.println("handle DbfFile error, py_script error");
            return false;
        }

    }

    /**
     * 执行python脚本
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean executePython(String filePath) throws IOException, InterruptedException{
        //前面一半是本地环境下的python的启动文件地址，后面一半是要执行的python脚本地址
        String[] arguments = new String[] {"python ","D:\\data\\py_script\\test.py ",filePath};
        Process proc;
        try {
            System.out.println(arguments);
            proc = Runtime.getRuntime().exec(arguments);// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            //waitFor是用来显示脚本是否运行成功，1表示失败，0表示成功，还有其他的表示其他错误
            System.out.println("----------------"+arguments);
            int re = proc.waitFor();
            System.out.println("-----------------------re:"+re);
            if(re==0){
                System.out.println("py脚本执行成功,数据转换完毕");
                return true;
            }
        } catch (IOException e) {
            System.out.println("----------------出错了");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

}
