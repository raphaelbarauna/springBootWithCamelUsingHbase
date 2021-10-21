package br.com.microservice.camela.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SystemDTO {

    private Boolean agregator;

    private List<String> reconciliation;

    private String acronym_name;


}
