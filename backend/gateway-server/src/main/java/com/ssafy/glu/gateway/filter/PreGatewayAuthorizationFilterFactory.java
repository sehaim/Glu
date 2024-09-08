package com.ssafy.glu.gateway.filter;

import com.ssafy.glu.gateway.util.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class PreGatewayAuthorizationFilterFactory extends AbstractGatewayFilterFactory<PreGatewayAuthorizationFilterFactory.Config> {
    private final JwtProvider jwtProvider;

    @Value("${server.auth.granted}")
    private String GRANTED;
    @Value("${server.header.user-id}")
    public String HEADER_USER_ID;

    public PreGatewayAuthorizationFilterFactory(JwtProvider jwtProvider){
        super(Config.class);
        this.jwtProvider = jwtProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            log.info("Authorization Header : "+authorizationHeader);
            if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(GRANTED+" ")) {
                String token = authorizationHeader.substring(7); // Bearer
                log.info("토큰 존재 : "+token);
                Claims claims = jwtProvider.verifyToken(token);

                log.info("claims 추출 : "+claims);
                Long userId = jwtProvider.getUserId(claims);// Extract userId from claims

                log.info("header에 userId 등록 => "+HEADER_USER_ID + " : " + userId);
                // Add userId to request headers
                ServerHttpRequest updatedRequest = request.mutate()
                        .header(HEADER_USER_ID, userId.toString())
                        .build();

                // Continue with the request
                return chain.filter(exchange.mutate().request(updatedRequest).build());
            }
            return unauthorizedResponse(exchange); // Token is not valid, respond with unauthorized
        };
    }

    // 인증 실패 Response
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Getter
    @Component
    public static class Config{
    }
}