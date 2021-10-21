package br.com.microservice.camelb.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SystemModel {

    private Integer systemId;

    private Boolean agregator;

    private List<String> reconciliation;

    private String acronym_name;


    public Integer getSystemId() {
        return systemId;
    }

    public Boolean getAgregator() {
        return agregator;
    }

    public List<String> getReconciliation() {
        return reconciliation;
    }

    public String getAcronym_name() {
        return acronym_name;
    }
}
