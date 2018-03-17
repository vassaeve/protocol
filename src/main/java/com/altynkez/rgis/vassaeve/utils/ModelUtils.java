package com.altynkez.rgis.vassaeve.utils;

import com.altynkez.rgis.vassaeve.entity.dto.PatientListViewDto;
import com.vassaeve.db.MyTableModel;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vassaeve
 */
public final class ModelUtils {

    public static MyTableModel<PatientListViewDto> createPatientModel() {
        MyTableModel<PatientListViewDto> patientModel = new MyTableModel<>(PatientListViewDto.class);
        patientModel.addColumnDescription(0, "regDate", "Дата регистр.");
        patientModel.addColumnDescription(1, "lastName", "Фамилия Имя Отчество");
        return patientModel;
    }

    public static Map<String, Float> createPatientColumnSize() {
        Map<String, Float> col_size = new HashMap<>(0);

        col_size.put("Дата регистр.", new Float(0.15));
        col_size.put("Фамилия Имя Отчество", new Float(0.85));     

        return col_size;
    }
}
