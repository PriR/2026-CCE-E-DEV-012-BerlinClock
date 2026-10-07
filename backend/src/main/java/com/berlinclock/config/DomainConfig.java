package com.berlinclock.config;

import com.berlinclock.domain.service.BerlinClock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Makes the framework-free domain available to Spring. */
@Configuration
public class DomainConfig {

    @Bean
    BerlinClock berlinClock() {
        return BerlinClock.standard();
    }
}
