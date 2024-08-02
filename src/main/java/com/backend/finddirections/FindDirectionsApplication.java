package com.backend.finddirections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FindDirectionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FindDirectionsApplication.class, args);
    }

}
