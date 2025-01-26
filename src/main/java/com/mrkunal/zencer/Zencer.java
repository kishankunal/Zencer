package com.mrkunal.zencer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mrkunal.zencer.module.ApplicationModule;
import com.mrkunal.zencer.module.ExternalModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = { JpaRepositoriesAutoConfiguration.class })
@ComponentScan(basePackages = "com.mrkunal.zencer") // Ensures Spring scans your components
public class Zencer {

	public static void main(String[] args) {
		SpringApplication.run(Zencer.class, args);
	}

	// Guice Injector that combines all modules
	@Bean
	public Injector guiceInjector() {
		return Guice.createInjector(
				new ExternalModule(),
				new ApplicationModule()
		);
	}
}