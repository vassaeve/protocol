package com.altynkez.rgis.vassaeve.helper;

import com.altynkez.rgis.vassaeve.entity.Services;
import com.altynkez.rgis.vassaeve.utils.EntityDescriptions;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vassaeve
 */
public final class ServicesHelper {

    public static Services createServicesEntity(ResultSet rs) throws SQLException, IllegalAccessException {
        return (Services) CommonHelper.createEntity(EntityDescriptions.SERVICES, rs);
    }

}
