package br.com.microservice.camela.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class System {

    private Boolean agregator;

    private List<String> reconciliation;

    private String acronym_name;


    @Override
    public String toString() {
        return "{" +
                "agregator: " + agregator +
                ", reconciliation: " + reconciliation +
                ", acronym_name:' " + acronym_name + '\'' +
                '}';
    }
}
