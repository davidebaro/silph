package it.uniroma3.bar.silph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication
public class SilphApplication {

	public static void main(String[] args) {
		SpringApplication.run(SilphApplication.class, args);
	}
}
