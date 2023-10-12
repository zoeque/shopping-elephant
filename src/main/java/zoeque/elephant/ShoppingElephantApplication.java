package zoeque.elephant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"zoeque.elephant"})
@EnableJpaRepositories(basePackages = "zoeque.elephant")
@ComponentScan(basePackages = {"zoeque.elephant"})
public class ShoppingElephantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingElephantApplication.class, args);
	}

}
