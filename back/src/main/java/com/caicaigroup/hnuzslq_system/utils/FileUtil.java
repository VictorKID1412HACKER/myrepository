package com.caicaigroup.hnuzslq_system.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.json.JSONObject;
import com.caicaigroup.hnuzslq_system.common.Constants;
import com.caicaigroup.hnuzslq_system.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 21g
 * @version 1.0
 * @className FileUtil
 * @since 1.0
 */
@Slf4j
@Component
public class FileUtil {

    private static String tempPath;

    @Value("${file.upload.url.temp}")
    public void setTempPath(String tempPath) {
        FileUtil.tempPath = tempPath;
    }

    private static String uploadDbfPath;

    @Value("${file.upload.url.dbf}")
    public void setUploadDbfPath(String uploadDbfPath) {
        FileUtil.uploadDbfPath = uploadDbfPath;
    }

    private static String uploadCsvPath;

    @Value("${file.upload.url.csv}")
    public void setUploadCsvPath(String uploadCsvPath) {
        FileUtil.uploadCsvPath = uploadCsvPath;
    }

    private static String uploadExcelPath;

    @Value("${file.upload.url.excel}")
    public void setUploadExcelPath(String uploadExcelPath) {
        FileUtil.uploadExcelPath = uploadExcelPath;
    }

    private static String uploadJpgPath;

    @Value("${file.upload.url.jpg}")
    public void setUploadJpgPath(String uploadJpgPath) {
        FileUtil.uploadJpgPath = uploadJpgPath;
    }

    /**
     * 上传文件（支持多文件）存储在配置文件中的路径
     * @param files 文件
     * @param suffix 后缀 (上传文件的文件夹路径后缀)
     * @return
     */
    public static String uploadFiles(MultipartFile files[],String suffix) {
        JSONObject object=new JSONObject();
        for(int i=0;i<files.length;i++){
            String fileName = files[i].getOriginalFilename(); // 文件名
            String type = fileName.substring(fileName.lastIndexOf(".")+1); // 获取文件类型（后缀）
            File dest = null; // 目标路径
            if ("dbf".equals(type)) {
                dest = new File(uploadDbfPath+"_"+suffix +'/'+ fileName);
            } else if ("xls".equals(type)) {
                dest = new File(uploadExcelPath +"_"+suffix +'/'+ fileName);
            } else if("JPG".equals(type)||"jpg".equals(type)){
                dest=new File(uploadJpgPath + "_"+suffix +'/'+ fileName);
            } else {
                throw new ServiceException(Constants.CODE_401, "文件格式错误");
            }
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);
            } catch (Exception e) {
                log.error("{}",e);
                object.put("success",2);
                object.put("result","程序错误，请重新上传");
                return object.toString();
            }
            System.out.println("文件成功上传服务器 路径为"+dest);
        }
        object.put("success",1);
        object.put("result","文件上传成功");
        return object.toString();
    }

    /**
     * 创建文件夹（目录），存在不可覆盖
     * @param destDirName 创建路径
     * @return true 成功 false 失败
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if(dir.exists()) {
//            System.out.println("创建目录" + destDirName + "失败，目标目录已存在！");
            return false;
        }
        if(!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        // 创建单个目录
        if(dir.mkdirs()) {
//            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        }else{
//            System.out.println("创建目录" + destDirName + "成功！");
            return false;
        }
    }

    /**
     * 删除当前操作人对应id下的临时文件夹 (dbf,csv)
     * @param id 当前操作人id
     * @return true 删除成功 false 删除失败
     */
    public static Boolean deleteTempFile(String id){
        String dbfTempFilePath=uploadDbfPath+"_"+id;
        String csvTempFilePath=uploadCsvPath+"_"+id;
        File dbfTempFile=new File(dbfTempFilePath);
        File csvTempFile=new File(csvTempFilePath);
        if(!deleteFile(dbfTempFile)||!deleteFile(csvTempFile)){
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return true;
    }

    /**
     * 删除当前操作人对应id下的照片临时文件夹
     * @param id id 当前操作人id
     * @return true 删除成功 false 删除失败
     */
    public static Boolean deleteJpgTempFile(String id){
        String jpgTempFilePath=uploadJpgPath+"_"+id;
        File jpgTempFile=new File(jpgTempFilePath);
        if(!deleteFile(jpgTempFile)){
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return true;
    }

    public static Boolean cshDeleteFile(){
        File datafile=new File(tempPath);
        if(!deleteFile(datafile)){
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return true;
    }

    /**
     * 删除文件夹以及文件夹下的文件
     * @param file 文件
     * @return true 删除成功 false 删除失败
     */
    public static Boolean deleteFile(File file) {
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            System.out.println("文件删除失败,请检查文件是否存在以及文件路径是否正确");
            return false;
        }
        //获取目录下子文件
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f : files) {
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()) {
                //递归删除目录下的文件
                deleteFile(f);
            } else {
                //文件删除
                f.delete();
                //打印文件名
//                System.out.println("删除文件：" + f.getName());
            }
        }
        //文件夹删除
        file.delete();
        System.out.println("删除目录：" + file.getName());
        return true;
    }

    /**
     * 创建excel并转换为字节流
     * @param list 需要写入excel的数据
     * @param title excel标题
     * @param sheetName 工作簿名
     * @param pojoClass 对应实体类
     * @param fileName 文件名
     * @return 转换为字节流的excel
     * @throws IOException
     */
    public static byte[] getExportExcelBytes(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title, sheetName), pojoClass, list);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        return os.toByteArray();
    }

    /**
     * 压缩文件并下载
     * @param outputStream
     * @param fileBufMap 需要被压缩的文件集合
     */
    public static void downloadZipForByteMore(OutputStream outputStream,Map<String,byte[]> fileBufMap)  {

        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(outputStream);
            for (String fileName:fileBufMap.keySet()){
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(zipEntry);
                if (Objects.nonNull(fileBufMap.get(fileName))){
                    byte[] fileBytes = fileBufMap.get(fileName);
                    zipOutputStream.write(fileBytes);
                    zipOutputStream.flush();
                }
            }
            zipOutputStream.flush();
            zipOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭流
            try {
                if (zipOutputStream != null ) {
                    zipOutputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 下载压缩包
     * @param downloadMap 压缩包中包含内容
     * @param fileName 压缩包文件名
     * @param response
     */
    public static void zipDownload(Map<String, byte[]> downloadMap, String fileName, HttpServletResponse response) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-strea");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        try {
            FileUtil.downloadZipForByteMore(response.getOutputStream(), downloadMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
