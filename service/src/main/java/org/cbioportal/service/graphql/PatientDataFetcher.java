package org.cbioportal.service.graphql;

import org.cbioportal.persistence.PatientRepository;
import org.cbioportal.model.Patient;

import java.util.*;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ochoaa
 */
@Component
public class PatientDataFetcher {
    @Autowired
    private PatientRepository patientRepository;

    public DataFetcher getData() {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment dfe) {
                Map<String, Object> study = dfe.getSource();
                String cancerStudyIdentifier = (String) study.get("cancerStudyIdentifier");

                List<Map<String, Object>> allPatients = new ArrayList<>();
                List<Patient> allPatientsForStudy = patientRepository.getAllPatientsInStudy(cancerStudyIdentifier, "DETAILED", 1000000, 0, null, "ASC");
                for (Patient pt : allPatientsForStudy) {
                    allPatients.add(getPatientMapObject(pt));
                }
                return allPatients;
            }
        };
    }

    private Map<String, Object> getPatientMapObject(Patient patient) {
        Map<String, Object> patientMap = new HashMap<>();
        patientMap.put("patientStableId", patient.getStableId());
        return patientMap;
    }
}
