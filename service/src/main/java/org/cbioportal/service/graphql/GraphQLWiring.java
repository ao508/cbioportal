package org.cbioportal.service.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphQLWiring {
    @Autowired
    CancerStudyFetcher cancerStudyFetcher;

    public RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query",typeWiring -> typeWiring
                       .dataFetcher("cancerStudy", cancerStudyFetcher.getData())
                )
                .build();
    }
}
