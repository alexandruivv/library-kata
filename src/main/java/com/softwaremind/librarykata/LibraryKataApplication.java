package com.softwaremind.librarykata;

import com.softwaremind.librarykata.config.ApplicationConfigParams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfigParams.class)
public class LibraryKataApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryKataApplication.class, args);
    }

}
