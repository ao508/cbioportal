package org.cbioportal.service.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphQLWiring {
    @Autowired
    private CancerStudyFetcher cancerStudyFetcher;

    @Autowired
    private PatientDataFetcher patientDataFetcher;

    public RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query",typeWiring -> typeWiring
                    .dataFetcher("cancerStudies", cancerStudyFetcher.getData()))
                .type("CancerStudy", typeWiring -> typeWiring
                    .dataFetcher("patients", patientDataFetcher.getData()))
                .build();
    }
}

