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

    @Description(value = "uid", PK = true)
    @Visible(order = 1)
    private String uid;

    @Description(value = "patientUid")
    private String patientUid;

    @Description("Дата")
    @Visible(order = 2)
    private Date createdDate;

}
