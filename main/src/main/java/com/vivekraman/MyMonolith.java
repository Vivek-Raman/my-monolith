package com.vivekraman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackageClasses = MyMonolith.class)
public class MyMonolith {
	public static void main(String[] args) {
		SpringApplication.run(MyMonolith.class, args);
	}
}
