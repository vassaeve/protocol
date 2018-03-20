package com.altynkez.rgis.vassaeve.facade;

import com.altynkez.rgis.vassaeve.exceptions.PatientFacadeException;
import com.altynkez.rgis.vassaeve.utils.Preferences;
import com.altynkez.rgis.vassaeve.ws.patients.PatientInterchangeEndPoint;
import com.altynkez.rgis.vassaeve.ws.patients.PatientInterchangeEndPointService;
import com.altynkez.rgis.vassaeve.ws.patients.SearchPatientResult;
import com.altynkez.rgis.vassaeve.ws.patients.XPatient;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Date;
import java.util.List;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author vassaeve
 */
public final class PatientWsFacade {

    public static final String ENDPOINT_ADDRESS = "/patients-smart-ws/patient";

    private static PatientInterchangeEndPoint getPort() {
        Preferences pref = Preferences.getInstance();
        Authenticator myAuth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(pref.getSetting(Preferences.USER_NAME), pref.getSetting(Preferences.USER_PSWD).toCharArray());
            }
        };
        Authenticator.setDefault(myAuth);
        PatientInterchangeEndPointService service = new PatientInterchangeEndPointService(PatientWsFacade.class.getResource("/wsdl/patients-smart-ws.wsdl"));
        PatientInterchangeEndPoint port = service.getPatientInterchangeEndPointPort();
        BindingProvider bindingProvider = (BindingProvider) port;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, pref.getSetting(Preferences.APP_SERVER) + ENDPOINT_ADDRESS);
        return port;
    }

    public static List<XPatient> simpleSearchPatient(String lastName, String firstName, String middleName, Date birth) throws PatientFacadeException {

        PatientInterchangeEndPoint port = getPort();
        Integer page = 1;
        SearchPatientResult result = port.searchPatient(page, lastName, firstName, middleName, null, null, null, null, null, null, null, null, null);
        if (result.getError() != null) {
            throw new PatientFacadeException(result.getError());
        }
        return result.getPatient();
    }

}
