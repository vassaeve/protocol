package com.altynkez.rgis.vassaeve.entity;

import com.altynkez.rgis.vassaeve.annotation.Description;
import com.altynkez.rgis.vassaeve.annotation.Visible;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author vassaeve
 */
@Data
@NoArgsConstructor
public class Cases {

    @Description(value = "id", PK = true)
    @Visible(order = 1)
    private String id;

    @Description(value = "uid")
    private String uid;

    @Description(value = "patientUid")
    private String patientUid;

    @Description("Дата")
    @Visible(order = 5)
    private Date createdDate;

}
