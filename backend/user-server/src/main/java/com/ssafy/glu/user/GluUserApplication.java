package com.ssafy.glu.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GluUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GluUserApplication.class, args);
    }

}
