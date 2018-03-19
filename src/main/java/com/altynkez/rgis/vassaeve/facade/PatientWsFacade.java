package com.altynkez.rgis.vassaeve.facade;

import com.altynkez.rgis.vassaeve.exceptions.PatientFacadeException;
import com.altynkez.rgis.vassaeve.ws.patients.PatientInterchangeEndPoint;
import com.altynkez.rgis.vassaeve.ws.patients.PatientInterchangeEndPointService;
import com.altynkez.rgis.vassaeve.ws.patients.SearchPatientResult;
import com.altynkez.rgis.vassaeve.ws.patients.XAddressSearch;
import com.altynkez.rgis.vassaeve.ws.patients.XIdentifier;
import com.altynkez.rgis.vassaeve.ws.patients.XPatient;
import com.vassaeve.commons.CalendarUtil;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vassaeve
 */
public final class PatientWsFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientWsFacade.class);

    public static List<XPatient> simpleSearchPatient(String lastName, String firstName, String middleName, Date birth) throws PatientFacadeException {

        try {
            PatientInterchangeEndPointService service = new PatientInterchangeEndPointService();
            PatientInterchangeEndPoint port = service.getPatientInterchangeEndPointPort();

            Integer page = 0;
            javax.xml.datatype.XMLGregorianCalendar birthDate = CalendarUtil.getXMLXMLGregorianCalendar(birth);

            List<XIdentifier> identifier = Collections.emptyList();
            List<XAddressSearch> addresses = Collections.emptyList();
            List<String> nearestRelative = Collections.emptyList();
            List<java.lang.String> citizenship = Collections.emptyList();

            Integer regClinicId = 0;
            String regClinicCode = "";
            String regClinicCodeType = "";

            XMLGregorianCalendar modifiedSince = CalendarUtil.getXMLXMLGregorianCalendar(new Date());

            SearchPatientResult result = port.searchPatient(page, lastName, firstName, middleName, birthDate, identifier, addresses, nearestRelative, citizenship, regClinicId, regClinicCode, regClinicCodeType, modifiedSince);

            if (result.getError() != null) {
                throw new PatientFacadeException(result.getError());
            }
            return result.getPatient();
        } catch (DatatypeConfigurationException ex) {
            LOGGER.error("{}", ex);
        }

        return Collections.emptyList();
    }

}
