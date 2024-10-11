package com.ssafy.glu.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GluAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GluAuthApplication.class, args);
    }

}
