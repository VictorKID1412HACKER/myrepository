package com.caicaigroup.hnuzslq_system.utils;

import com.caicaigroup.hnuzslq_system.mapper.TableMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
class CSVUtilsTest {

    @Autowired
    private TableMapper tableMapper;

    @Test
    void test(){
        File file=new File("D:\\data\\csv\\t_tdd.csv");
//        CSVUtils.insertData(file);
    }

}