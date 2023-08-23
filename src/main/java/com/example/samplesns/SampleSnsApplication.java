package com.example.samplesns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SampleSnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleSnsApplication.class, args);
    }

}
