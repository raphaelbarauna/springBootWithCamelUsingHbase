package br.com.microservice.camela.config;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GetCurrentTimeBean {

    public String getCurrenTime() {

        return "Time now is " + LocalDateTime.now();
    }

}
