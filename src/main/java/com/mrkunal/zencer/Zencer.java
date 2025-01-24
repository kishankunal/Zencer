package com.mrkunal.zencer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ComponentScan(basePackages = "com.mrkunal.zencer")
@SpringBootApplication
public class Zencer {

	public static void main(String[] args) {
		SpringApplication.run(Zencer.class, args);
	}

}
