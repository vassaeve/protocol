package com.altynkez.rgis.vassaeve.facade;

import com.altynkez.rgis.vassaeve.ws.cases.MedicalCase;
import com.altynkez.rgis.vassaeve.ws.cases.MedicalCaseCriteria;
import com.altynkez.rgis.vassaeve.ws.cases.MedicalCaseId;
import com.altynkez.rgis.vassaeve.ws.cases.MedicalCasesPortType;
import com.altynkez.rgis.vassaeve.ws.cases.MedicalCasesService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vassaeve
 */
public final class CasesWsFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CasesWsFacade.class);

    public static MedicalCase getCaseById(String caseId) {
        MedicalCasesService service = new MedicalCasesService();
        MedicalCasesPortType port = service.getMedicalCasesPort();
        MedicalCaseId getCaseByIdRequest = new MedicalCaseId();
        getCaseByIdRequest.setId(caseId);
        return port.getCaseById(getCaseByIdRequest);
    }

    public static List<MedicalCase> getCasesByPatientUid(String patientUid) {
        MedicalCasesService service = new MedicalCasesService();
        MedicalCasesPortType port = service.getMedicalCasesPort();
        MedicalCaseCriteria medicalCaseCriteria = new MedicalCaseCriteria();
        medicalCaseCriteria.setPatientUid(patientUid);

        List<String> ids = port.searchCase(medicalCaseCriteria).getIds();
        List<MedicalCase> result = new ArrayList<>(0);
        ids.forEach((id) -> {
            result.add(getCaseById(id));
        });
        return result;
    }

}
