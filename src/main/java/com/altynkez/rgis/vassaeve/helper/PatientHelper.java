package com.altynkez.rgis.vassaeve.helper;

import com.altynkez.rgis.vassaeve.entity.Patient;
import com.altynkez.rgis.vassaeve.utils.EntityDescriptions;
import com.altynkez.rgis.vassaeve.ws.XPatient;
import com.vassaeve.commons.CalendarUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.lang.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vassaeve
 */
public class PatientHelper {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientHelper.class);
    
    public static Patient createPatientEntity(ResultSet rs) throws SQLException, IllegalAccessException {
        Patient entity = new Patient();
        
        Map<String, EntityDescriptions.FieldDescription> descriptions = EntityDescriptions.PATIENT.getFieldsDescriptions();
        EntityDescriptions.FieldDescription fieldDescription;
        Object value;
        for (String field : descriptions.keySet()) {
            fieldDescription = descriptions.get(field);
            switch (fieldDescription.getType().getTypeName()) {
                case "java.lang.String":
                    value = rs.getString(field);
                    break;
                default:
                    value = rs.getObject(field);
            }
            FieldUtils.writeField(entity, field, value, true);
        }
        return entity;
    }
    
    public static Patient createPatient(XPatient patient) {
        Patient entity = new Patient();
        try {
            entity.setBirth(CalendarUtil.toDate(patient.getBirthDate()));
        } catch (DatatypeConfigurationException ex) {
            LOGGER.error("{}", ex);
        }
        
        return entity;
    }
    
}
