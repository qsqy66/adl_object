package com.example.adl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.adl.mapper")
@SpringBootApplication
public class AdlApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdlApplication.class, args);
    }

}
