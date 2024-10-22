package com.caicaigroup.hnuzslq_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caicaigroup.hnuzslq_system.common.Constants;
import com.caicaigroup.hnuzslq_system.entity.Fbpz;
import com.caicaigroup.hnuzslq_system.entity.Zd;
import com.caicaigroup.hnuzslq_system.entity.Zydm;
import com.caicaigroup.hnuzslq_system.exception.ServiceException;
import com.caicaigroup.hnuzslq_system.mapper.FbpzMapper;
import com.caicaigroup.hnuzslq_system.mapper.ZdMapper;
import com.caicaigroup.hnuzslq_system.mapper.ZydmMapper;
import com.caicaigroup.hnuzslq_system.service.FbpzService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.InputStream;

@Transactional
@Service
public class FbpzServiceImpl extends ServiceImpl<FbpzMapper, Fbpz> implements FbpzService {

    @Value("${file.upload.url.excel}")
    private String uploadExcelPath;

    @Autowired
    private FbpzMapper fbpzMapper;

    @Autowired
    private ZdMapper zdMapper;

    @Autowired
    private ZydmMapper zydmMapper;

    @Override
    public void importExcel(String filename) {
        try {

            // 获取文件输入流
            String path = uploadExcelPath + '/' + filename;
            System.out.println(path);
            InputStream inputStream = new FileInputStream(uploadExcelPath + '/' + filename);

            // 定义一个org.apache.poi.ss.usermodel.Workbook的变量
            Workbook workbook = null;
            // 截取路径名 . 后面的后缀名，判断是xls还是xlsx
            // 如果这个判断不对，就把equals换成 equalsIgnoreCase()
            if (path.substring(path.lastIndexOf(".") + 1).equalsIgnoreCase("xls")){
                workbook = new HSSFWorkbook(inputStream);
            }else if (path.substring(path.lastIndexOf(".") + 1).equalsIgnoreCase("xlsx")){
                workbook = new XSSFWorkbook(inputStream);
            }

            // 获取第一张表
            Sheet sheet = workbook.getSheetAt(0);
            // sheet.getPhysicalNumberOfRows()获取总的行数
            // 循环读取每一行
            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                if (i == 0) {
                    continue;
                }
                // 循环读取每一个格
                Row row = sheet.getRow(i);

                Fbpz fbpz = new Fbpz();

                // row.getPhysicalNumberOfCells()获取总的列数
                for (int index = 0; index < row.getPhysicalNumberOfCells(); index++) {
                    // 获取数据，但是我们获取的cell类型
//代码上的内容自己根据实际需要自己调整就可以，这里只是展示一个样式···~
                    Cell cell = row.getCell(index);
                    // 转换为字符串类型
                    cell.setCellType(CellType.STRING);
                    // 获取得到字符串
                    String value = cell.getStringCellValue();

                    cellValueMap(index, value, fbpz);
                }

                QueryWrapper<Fbpz> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("zydh", fbpz.getZydh());
                Fbpz one = fbpzMapper.selectOne(queryWrapper);
                if (one == null) {
                    save(fbpz);
                } else {
                    update(fbpz, queryWrapper);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_401, "请重新检查上传文件！");
        }

    }

    /**
     * 读取每一列的值
     * @param index 下标
     * @param value 数据值
     * @param fbpz 分班配置
     * @throws Exception
     */
    private void cellValueMap(int index, String value, Fbpz fbpz) throws Exception{
        if (index == 0) {
            if ("建筑学院".equals(value)) {
              value = "建筑与规划学院";
            }
            QueryWrapper<Zd> queryWrapper = new QueryWrapper<Zd>();
            queryWrapper.eq("dmmc", value);
            String yxdm = (zdMapper.selectOne(queryWrapper)).getDm();
            fbpz.setYxdm(yxdm);
        }
        if (index == 1) {
            QueryWrapper<Zydm> queryWrapper = new QueryWrapper<Zydm>();
            queryWrapper.eq("zymc", value);
            queryWrapper.eq("year", 2021);

            String zydh = (zydmMapper.selectOne(queryWrapper)).getZydh();
            fbpz.setZydh(zydh);
        }
        if (index == 2) {
            fbpz.setBjsl(Integer.parseInt(value));
        }
        if (index == 3) {
            fbpz.setBjdmqz(value);
        }
        if (index == 4) {
            fbpz.setBjmcqz(value);
        }

    }
}
