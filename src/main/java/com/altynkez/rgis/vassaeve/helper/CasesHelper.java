package com.altynkez.rgis.vassaeve.helper;

import com.altynkez.rgis.vassaeve.entity.Cases;
import com.altynkez.rgis.vassaeve.utils.EntityDescriptions;
import com.altynkez.rgis.vassaeve.ws.cases.MedicalCase;
import com.vassaeve.commons.CalendarUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.datatype.DatatypeConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vassaeve
 */
public final class CasesHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CasesHelper.class);

    public static Cases createCasesEntity(ResultSet rs) throws SQLException, IllegalAccessException {
        return (Cases) CommonHelper.createEntity(EntityDescriptions.CASES, rs);
    }

    public static Cases createCases(MedicalCase cases) {
        Cases entity = new Cases();
        entity.setUid(cases.getUid());
        try {
            entity.setCreatedDate(CalendarUtil.toDate(cases.getCreatedDate()));
        } catch (DatatypeConfigurationException ex) {
            LOGGER.error("{}", ex);
        }

        return entity;
    }

}
