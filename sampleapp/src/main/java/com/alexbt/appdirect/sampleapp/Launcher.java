package com.alexbt.appdirect.sampleapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Spring Boot start-class
 * @author alexbt
 *
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class Launcher {

    protected Launcher() {
    }

    /**
     * Spring Boot main method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder() //
                .sources(Launcher.class)//
                .run(args);
    }
}
