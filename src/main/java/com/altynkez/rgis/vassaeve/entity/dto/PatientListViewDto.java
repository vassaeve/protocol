package com.altynkez.rgis.vassaeve.entity.dto;

import com.altynkez.rgis.vassaeve.entity.Patient;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author vassaeve
 */
@Data
@NoArgsConstructor
public class PatientListViewDto implements Serializable {

    private static final long serialVersionUID = -6746872199523083134L;

    private Long id;
    private String fio;

    public PatientListViewDto(Patient pat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
