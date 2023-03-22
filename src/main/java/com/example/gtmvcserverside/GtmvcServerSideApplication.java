package com.example.gtmvcserverside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GtmvcServerSideApplication {

    public static void main(String[] args) {
        SpringApplication.run(GtmvcServerSideApplication.class, args);
    }

}
