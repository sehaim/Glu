package com.ssafy.glu.gateway.config;

import com.ssafy.glu.gateway.filter.PreGatewayAuthorizationFilterFactory;
import com.ssafy.glu.gateway.util.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    PreGatewayAuthorizationFilterFactory preGatewayAuthorizationFilterFactory(JwtProvider jwtProvider) {
        return new PreGatewayAuthorizationFilterFactory(jwtProvider);
    }
}