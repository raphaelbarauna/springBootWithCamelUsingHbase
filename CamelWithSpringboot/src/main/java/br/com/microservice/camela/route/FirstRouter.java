package br.com.microservice.camela.route;

import br.com.microservice.camela.config.GetCurrentTimeBean;
import br.com.microservice.camela.config.SimpleLogginProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class FirstRouter extends RouteBuilder {

    @Autowired
    GetCurrentTimeBean currentTimeBean;

    @Override
    public void configure() throws Exception {

        from("timer:first-timer")
                .log("${body}")
                .transform().constant("My constant")
                .to("log:first-timer")


                .bean(currentTimeBean)
                .log("${body}")
                .process( new SimpleLogginProcessor())
                .log("log:first-timer");
    }
}
