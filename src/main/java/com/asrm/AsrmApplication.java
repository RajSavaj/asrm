package com.asrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AsrmApplication extends SpringBootServletInitializer  { //

	public static void main(String[] args) {
		SpringApplication.run(AsrmApplication.class, args);
	}
	 @Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	       return builder.sources(AsrmApplication.class);
	 }
}
