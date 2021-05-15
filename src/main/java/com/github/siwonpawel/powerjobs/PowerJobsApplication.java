package com.github.siwonpawel.powerjobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties
public class PowerJobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PowerJobsApplication.class, args);
	}

}
