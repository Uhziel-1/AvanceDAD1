package com.example.msproductoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsProductoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProductoServiceApplication.class, args);
    }

}
