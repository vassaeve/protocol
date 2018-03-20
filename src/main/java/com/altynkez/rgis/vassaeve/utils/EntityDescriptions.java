package com.altynkez.rgis.vassaeve.utils;

import com.altynkez.rgis.vassaeve.annotation.Description;
import com.altynkez.rgis.vassaeve.annotation.Visible;
import com.altynkez.rgis.vassaeve.entity.Cases;
import com.altynkez.rgis.vassaeve.entity.Patient;
import com.altynkez.rgis.vassaeve.entity.Services;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by e.vassaev
 */
public enum EntityDescriptions {

    /**
     * Наименование таблицы и pojo-класса
     */
    CASES(initDescriptions(Cases.class)),
    SERVICES(initDescriptions(Services.class)),
    PATIENT(initDescriptions(Patient.class));

    private final Map<String, FieldDescription> fieldsDescriptions;
    private final String primaryKeyFieldName;
    private final String INSERT_SQL;
    private final String UPDATE_SQL;
    private final String DELETE_SQL;

    EntityDescriptions(EntityDescription description) {

        //сразу отсортируем по атрибуту order
        this.fieldsDescriptions = description.fieldsDescriptions.entrySet()
                .stream()
                .sorted(
                        Map.Entry.comparingByValue(
                                (o1, o2)
                                -> {
                            return o1.getOrder() - o2.getOrder();
                        })
                )
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new
                        )
                );

        this.primaryKeyFieldName = description.getPrimaryKeyFieldName();

        String fields = String.join(", ", fieldsDescriptions.keySet());
        String questions = String.join(",", Collections.nCopies(fieldsDescriptions.size(), "?"));
        this.INSERT_SQL = String.format("INSERT INTO %s (%s) values (%s)", this.name(), fields, questions);

        String setClause = String.join(
                ", ",
                fieldsDescriptions.keySet()
                        .stream()
                        .filter(
                                s -> {
                                    return !s.equals(primaryKeyFieldName);
                                }
                        ).map(
                                s -> {
                                    return s + "=?";
                                }
                        ).collect(Collectors.toList())
        );
        String whereClause = primaryKeyFieldName + "=?";

        this.UPDATE_SQL = String.format("UPDATE %s set %s WHERE %s", this.name(), setClause, whereClause);

        this.DELETE_SQL = String.format("DELETE FROM %s WHERE %s", this.name(), whereClause);
    }

    public String getPrimaryKeyFieldName() {
        return primaryKeyFieldName;
    }

    public String getUPDATE_SQL() {
        return UPDATE_SQL;
    }

    public String getINSERT_SQL() {
        return INSERT_SQL;
    }

    public String getDELETE_SQL() {
        return DELETE_SQL;
    }

    public Map<String, FieldDescription> getFieldsDescriptions() {
        return fieldsDescriptions;
    }

    private static EntityDescription initDescriptions(Class<?> cls) {
        Map<String, FieldDescription> result = new HashMap<>();
        String primaryKeyFieldName = null;
        Field[] declaredFields = cls.getDeclaredFields();
        int order;
        for (Field field : declaredFields) {
            Description declaredAnnotation = field.getDeclaredAnnotation(Description.class);
            if (declaredAnnotation != null) {
                Visible visible = field.getDeclaredAnnotation(Visible.class);
                if (visible != null) {
                    order = visible.order();
                } else {
                    order = Integer.MAX_VALUE;
                }

                result.put(field.getName(), new FieldDescription(visible != null, order, declaredAnnotation.value(), field.getGenericType()));
                if (declaredAnnotation.PK() && primaryKeyFieldName == null) {
                    primaryKeyFieldName = field.getName();
                }
            }
        }
        return new EntityDescription(result, primaryKeyFieldName);
    }

    public static class FieldDescription {

        private final String description;
        private final boolean visible;
        private final int order;
        private final Type type;

        public FieldDescription(boolean visible, int order, String description, Type type) {
            this.order = order;
            this.description = description;
            this.type = type;
            this.visible = visible;
        }

        public String getDescription() {
            return description;
        }

        public int getOrder() {
            return order;
        }

        public boolean isVisible() {
            return visible;
        }

        @Override
        public String toString() {
            return "FieldDescription{" + "order=" + order + ", type=" + type + '}';
        }

        public Type getType() {
            return type;
        }
    }

    private static class EntityDescription {

        private final Map<String, FieldDescription> fieldsDescriptions;
        private final String primaryKeyFieldName;

        public EntityDescription(Map<String, FieldDescription> fieldsDescriptions, String primaryKeyFieldName) {
            this.fieldsDescriptions = fieldsDescriptions;
            this.primaryKeyFieldName = primaryKeyFieldName;
        }

        public String getPrimaryKeyFieldName() {
            return primaryKeyFieldName;
        }

        public Map<String, FieldDescription> getFieldsDescriptions() {
            return fieldsDescriptions;
        }
    }
}
