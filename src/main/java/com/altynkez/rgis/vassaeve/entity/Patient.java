package com.altynkez.rgis.vassaeve.entity;

import com.altynkez.rgis.vassaeve.annotation.Description;
import com.altynkez.rgis.vassaeve.annotation.Visible;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author vassaeve
 */
@Data
public final class Patient {

    @Description(value = "ID", PK = true)
    @Visible(order = 1)
    private String id;

    @Description(value = "uid")
    private String uid;

    @Description("Фамилия пациента")
    private String lastName;

    @Description("Имя")
    private String firstName;

    @Description("Отчество")
    private String middleName;

    @Description("Дата рождения")
    private Date birth;

    public Patient() {
    }

//    class Builder {
//        private Builder() {
//        }
//        public Builder setLastName(String lastName) {
//            Patient.this.lastName = lastName;
//            return this;
//        }
//
//        public Builder setFirstName(String firstName) {
//            Patient.this.firstName = firstName;
//            return this;
//        }
//
//        public Builder setMiddleName(String middleName) {
//            Patient.this.middleName = middleName;
//            return this;
//        }
//
//        public Patient build() {
//            return Patient.this;
//        }
//    }
}
