package com.caicaigroup.hnuzslq_system.initializer;

import com.caicaigroup.hnuzslq_system.entity.User;
import com.caicaigroup.hnuzslq_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.getUserList().isEmpty()) {
            User defaultUser = new User();
            defaultUser.setUserId("admin");
            defaultUser.setUserPwd("admin");  // 切勿在生产中使用简单密码
            userService.addUser(defaultUser);
            System.out.println("Default admin user created.");
        }
    }
}
