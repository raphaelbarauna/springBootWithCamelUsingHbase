package br.com.microservice.camela.route;

import br.com.microservice.camela.model.System;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestApiRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration().host("localhost").port(8081);
//
//        from("timer:rest-api-consumer?period=10000")
//                .routeId("GET-Systems")
//                .to("rest:get:/hbase")
//                .process();

        from("timer:rest-api-consumer?period=10000")
                .routeId("GET-Systems-byId")
                .setHeader("system_id", () -> "1")
                .to("rest:get:/hbase/{system_id}")
                .log("${body}");


        from("timer:rest-api-consumer?period=10000")
                .routeId("DELETE-Systems-byId")
                .removeHeaders("CamelHttp*")
                .setHeader("system_id", () -> "1")
                .to("rest:delete:/hbase/{system_id}")
                .removeHeaders("*")
                .log("${body}");

        from("timer:rest-api-consumer?period=10000")
                .routeId("POST-Systems-byId")
                .process(exchange -> exchange.getIn().setBody(createSystemJson()))
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("rest:post:/hbase")
                .log("${body}");

        from("timer:rest-api-consumer?period=10000")
                .routeId("PUT-Systems-byId")
                .process(exchange -> exchange.getIn().setBody(createSystemJson()))
                .setHeader("system_id", () -> "2")
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("rest:put:/hbase/{system_id}")
                .log("${body}");

        from("timer:rest-api-consumer?period=10000")
                .routeId("GET-Systems-ALL")
                .to("rest:get:/hbase/all")
                .log("${body}");

    }

    public String createSystemJson() throws JsonProcessingException {

        List<String> list = new ArrayList<>();
        list.add("teste");
        list.add("teste2");
        System system = new System(true, list, "AT");

        Gson gson = new Gson();
        return gson.toJson(system);
    }

}
