package com.dubbo.bootorderservicecunsumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableDubbo
@EnableHystrix
@SpringBootApplication
public class BootOrderServiceCunsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootOrderServiceCunsumerApplication.class, args);
    }

}
