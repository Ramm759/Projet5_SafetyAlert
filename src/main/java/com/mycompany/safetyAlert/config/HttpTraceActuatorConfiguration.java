package com.mycompany.safetyAlert.config;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// Depuis version 2.2.0, endPoint HttpTrace supprimé
@Configuration
public class HttpTraceActuatorConfiguration {
    @Bean
    public HttpTraceRepository httpTraceRepository (){
        return new InMemoryHttpTraceRepository();
    }
}
