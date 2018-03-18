package com.altynkez.rgis.vassaeve.entity.dto;

import com.altynkez.rgis.vassaeve.entity.Patient;
import java.io.Serializable;
import java.util.Date;
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

    private String uid;
    private Date birth;
    private String fio;

    public PatientListViewDto(Patient pat) {
        this.uid = pat.getUid();
        this.birth = pat.getBirth();
        this.fio = String.format("%s %s %s", pat.getLastName(), pat.getFirstName(), pat.getMiddleName());
    }

}
