package com.mixajlenko.ispspring;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IspSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(IspSpringApplication.class, args);
        BasicConfigurator.configure();
    }


}
