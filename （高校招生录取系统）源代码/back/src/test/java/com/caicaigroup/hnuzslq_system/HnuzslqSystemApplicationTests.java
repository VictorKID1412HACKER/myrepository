package com.caicaigroup.hnuzslq_system;

import com.caicaigroup.hnuzslq_system.utils.DBFUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class HnuzslqSystemApplicationTests {

    @Test
    void contextLoads() {
    }

    @Value("${file.download.url.oldDatabases}")
    String filePath;

    @Test
    void testFilePath(){
        System.out.println(filePath);
    }

}