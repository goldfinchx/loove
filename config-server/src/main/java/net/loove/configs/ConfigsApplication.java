package net.loove.configs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigServer
@SpringBootApplication
public class ConfigsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigsApplication.class, args);
	}

}