package com.softwaremind.librarykata.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.config")
@Getter
@Setter
public class ApplicationConfigParams {
    private int daysOfRental;
}
