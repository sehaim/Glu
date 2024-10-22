package com.ssafy.glu.problem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableMongoAuditing
@EnableFeignClients
@EnableKafka
@EnableScheduling
public class GluProblemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GluProblemApplication.class, args);
    }

}
