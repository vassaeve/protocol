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
public class Services {

    @Description(value = "id", PK = true)
    @Visible(order = 1)
    private Long id;

    @Description(value = "casesuid")
    private String casesuid;

    @Description(value = "protocol")
    private String protocol;
    
    @Description(value = "Жалоба")
     @Visible(order = 2)
    private String zzzz;

    @Description("Дата")
    @Visible(order = 3)
    private Date createdDate;

}
