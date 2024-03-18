package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;



@ServletComponentScan
@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class Demo5Application {

    public static void main(String[] args)/* throws IOException */{
        SpringApplication.run(Demo5Application.class, args);

    }

}
