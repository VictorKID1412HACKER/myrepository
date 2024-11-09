package com.caicaigroup.hnuzslq_system.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JPGUtil {

    private static JPGUtil jpgUtil;

    @PostConstruct
    public void init(){
        jpgUtil = this ;
    }

    private static String uploadJpgPath;

    @Value("${file.upload.url.jpg}")
    public void setUploadJpgPath(String uploadJpgPath) {
        JPGUtil.uploadJpgPath = uploadJpgPath;
    }

    /**
     * 处理图片文件夹 获取考生号和对应图片的二进制串
     * @param id id 当前登陆用户id
     * @return map 考生号和对应图片的二进制串map集合
     */
    public static Map<String,byte[]> handleImageToBlob(String id){
        Map<String,byte[]> imageMap = new HashMap<>();
        String jpgPath=uploadJpgPath+"_"+id;
        File f=new File(jpgPath);
        File [] files=f.listFiles();
        for(File file:files) {
            String filePath=file.getName();
            String fileName=filePath.substring(0,filePath.lastIndexOf("."));
            byte[] b=jpgUtil.imageToBlob(file);
            imageMap.put(fileName,b);
        }
        return imageMap;
    }

    /**
     * 把图片文件转换成可以存入数据库的blob类型
     * @param file 照片文件
     * @return byte[] 二进制
     */
    @SneakyThrows //处理异常
    public byte[] imageToBlob(File file){
        byte[] imageBytes = null;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            imageBytes = new byte[fileInputStream.available()];
            fileInputStream.read(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBytes;
    }

    /**
     * 把图片二进制串转换成图片保存在参数路径中
     * @param imageBytes 图片二进制串
     * @param imageFilePath 图片存储路径
     */
    @SneakyThrows //处理异常
    public static void blobToImage(byte[] imageBytes,String imageFilePath){
        FileOutputStream fileOutputStream = new FileOutputStream(imageFilePath);
        fileOutputStream.write(imageBytes);
    }

    @Test
    public void test(){
        handleImageToBlob("19");
    }

}
