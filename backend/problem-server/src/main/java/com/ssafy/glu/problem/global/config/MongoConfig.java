package com.ssafy.glu.problem.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.ssafy.glu.problem")
@EnableMongoAuditing
public class MongoConfig {
    // 필요 시 추가적인 설정 작성
}