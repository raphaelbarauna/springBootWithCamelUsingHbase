package br.com.microservice.camela.processor;

import br.com.microservice.camela.model.System;
import br.com.microservice.camela.model.dto.SystemDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.List;

public class TransformCreateRequest implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        SystemDTO systemDTO = exchange.getMessage().getBody(SystemDTO.class);



    }

    public SystemDTO createSystemJson() throws JsonProcessingException {

        List<String> list = new ArrayList<>();
        list.add("teste");
        list.add("teste2");
        return new SystemDTO(true, list, "AT");

    }
}
