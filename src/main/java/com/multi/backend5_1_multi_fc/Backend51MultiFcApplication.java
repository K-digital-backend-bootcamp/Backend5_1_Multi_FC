package com.multi.backend5_1_multi_fc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.multi.backend5_1_multi_fc")
public class Backend51MultiFcApplication {

    public static void main(String[] args) {
        SpringApplication.run(Backend51MultiFcApplication.class, args);
    }

}
