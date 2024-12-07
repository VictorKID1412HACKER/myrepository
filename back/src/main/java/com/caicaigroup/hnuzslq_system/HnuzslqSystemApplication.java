package com.caicaigroup.hnuzslq_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HnuzslqSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HnuzslqSystemApplication.class, args);
    }

}
