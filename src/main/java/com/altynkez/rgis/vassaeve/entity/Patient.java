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

    @Description(value = "id", PK = true)
    @Visible(order = 1)
    private String id;

    @Description(value = "uid")
    private String uid;

    @Description("Фамилия пациента")
    @Visible(order = 2)
    private String lastName;

    @Description("Имя")
    @Visible(order = 3)
    private String firstName;

    @Description("Отчество")
    @Visible(order = 4)
    private String middleName;

    @Description("Дата рождения")
    @Visible(order = 5)
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
