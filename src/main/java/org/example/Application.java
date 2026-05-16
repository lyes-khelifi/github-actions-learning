package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.repository")
@ComponentScan(basePackages = "org.example")
public class Application {

    /**
 * Private constructor to prevent instantiation.
 */
public Application() {
}

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
