package com.agufish.project;

import com.agufish.project.view.Menu;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;


@MapperScan("com.agufish.project.mapper")
@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    @Resource
    private Menu menu;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }


    // 回调函数
    @Override
    public void run(String... args) throws Exception {
        menu.mainMenu();
    }
}
