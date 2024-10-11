package com.ssafy.glu.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableKafka
public class GluUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GluUserApplication.class, args);
    }

}
