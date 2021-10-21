package br.com.microservice.camelb.model.dto;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SystemDTO {

    private Integer systemId;

    private Boolean agregator;

    private List<String> reconciliation;

    private String acronym_name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemDTO systemDTO = (SystemDTO) o;
        return Objects.equals(agregator, systemDTO.agregator) &&
                Objects.equals(reconciliation, systemDTO.reconciliation) &&
                Objects.equals(acronym_name, systemDTO.acronym_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agregator, reconciliation, acronym_name);
    }
}
