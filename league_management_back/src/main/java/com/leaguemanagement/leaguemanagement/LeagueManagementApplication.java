package com.leaguemanagement.leaguemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCaching
public class LeagueManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeagueManagementApplication.class, args);
	}

}
