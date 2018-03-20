package com.altynkez.rgis.vassaeve.helper;

import com.altynkez.rgis.vassaeve.entity.Cases;
import com.altynkez.rgis.vassaeve.entity.Patient;
import com.altynkez.rgis.vassaeve.entity.Services;
import com.altynkez.rgis.vassaeve.utils.EntityDescriptions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.apache.commons.lang.reflect.FieldUtils;

/**
 *
 * @author vassaeve
 */
public class CommonHelper {

    public static Object createEntity(EntityDescriptions entityDescriptions, ResultSet rs) throws SQLException, IllegalAccessException {
        Object entity;
        switch (entityDescriptions) {
            case PATIENT:
                entity = new Patient();
                break;
            case CASES:
                entity = new Cases();
                break;
            case SERVICES:
                entity = new Services();
                break;
            default:
                entity = new Cases();
        }

        Map<String, EntityDescriptions.FieldDescription> descriptions = entityDescriptions.getFieldsDescriptions();
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
}
