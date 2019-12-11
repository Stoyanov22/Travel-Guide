package uni.travelguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"uni.travelguide"})
@EnableAutoConfiguration
public class TravelGuideApplication {

	public static void main(String[] args) {
		/* Responsible for launching the boot application. */
		SpringApplication.run(TravelGuideApplication.class, args);
	}
}