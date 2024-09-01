package com.ssafy.glu.problem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GluProblemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GluProblemApplication.class, args);
    }

}
