package com.mattdion.skyblockbazaar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class SkyblockBazaarApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyblockBazaarApplication.class, args);
    }

    @RequestMapping("/test")
    @ResponseBody
    String home() {
        return "Hello World! TEST";
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("App started");
        };
    }
}
