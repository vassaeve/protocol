package com.altynkez.rgis.vassaeve.facade;

import com.altynkez.rgis.vassaeve.utils.Preferences;
import com.altynkez.rgis.vassaeve.ws.cases.MedicalCase;
import com.altynkez.rgis.vassaeve.ws.cases.MedicalCaseCriteria;
import com.altynkez.rgis.vassaeve.ws.cases.MedicalCaseId;
import com.altynkez.rgis.vassaeve.ws.cases.MedicalCaseIds;
import com.altynkez.rgis.vassaeve.ws.cases.MedicalCasesPortType;
import com.altynkez.rgis.vassaeve.ws.cases.MedicalCasesService;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.BindingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vassaeve
 */
public final class CasesWsFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CasesWsFacade.class);

    public static final String ENDPOINT_ADDRESS = "/cases-ws/cases";

    private static MedicalCasesPortType getPort() {
        Preferences pref = Preferences.getInstance();
        Authenticator myAuth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(pref.getSetting(Preferences.USER_NAME), pref.getSetting(Preferences.USER_PSWD).toCharArray());
            }
        };
        Authenticator.setDefault(myAuth);
        MedicalCasesService service = new MedicalCasesService(CasesWsFacade.class.getResource("/wsdl/cases-ws.wsdl"));
        MedicalCasesPortType port = service.getMedicalCasesPort();
        BindingProvider bindingProvider = (BindingProvider) port;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, pref.getSetting(Preferences.APP_SERVER) + ENDPOINT_ADDRESS);
        return port;
    }

    public static MedicalCase getCaseById(String caseId) {
        MedicalCasesPortType port = getPort();
        MedicalCaseId getCaseByIdRequest = new MedicalCaseId();
        getCaseByIdRequest.setId(caseId);
        return port.getCaseById(getCaseByIdRequest);
    }

    public static List<MedicalCase> getCasesByPatientUid(String patientUid) {
        List<MedicalCase> result = new ArrayList<>(0);
        MedicalCasesPortType port = getPort();

        MedicalCaseCriteria medicalCaseCriteria = new MedicalCaseCriteria();
        medicalCaseCriteria.setPatientUid(patientUid);
        medicalCaseCriteria.setMedicalOrganizationId("");
        medicalCaseCriteria.setCaseTypeId("");

        MedicalCaseIds caseIds = port.searchCase(medicalCaseCriteria);
        if (caseIds != null) {
            List<String> ids = caseIds.getIds();

            ids.forEach((id) -> {
                result.add(getCaseById(id));
            });
        }
        return result;
    }

}
