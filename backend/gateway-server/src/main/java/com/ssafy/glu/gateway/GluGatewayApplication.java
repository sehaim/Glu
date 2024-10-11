package com.ssafy.glu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GluGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GluGatewayApplication.class, args);
	}
}
