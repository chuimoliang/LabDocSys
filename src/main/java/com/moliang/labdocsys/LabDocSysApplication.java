package com.moliang.labdocsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.moliang.labdocsys.mapper")
public class LabDocSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabDocSysApplication.class, args);
    }

}
