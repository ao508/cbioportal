package org.cbioportal.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.*;
import java.util.*;

@Repository
public class GraphQLSchemaRepository {

    private TypeDefinitionRegistry registry;

    @Autowired
    private void loadSchema() {
        SchemaParser schemaParser = new SchemaParser();
        String portalHome = System.getenv("PORTAL_HOME");
        if (portalHome == null) {
            throw new RuntimeException("error: PORTAL_HOME environment variable is not set");
        }
        File schemaFile = new File(portalHome, "src/main/resources/cbioportal.graphql");
        if (!schemaFile.exists()) {
            throw new RuntimeException("error: cbioportal.graphql does not exist in src/main/resources");
        }
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schemaFile);
        registry = typeDefinitionRegistry;
    }

    public TypeDefinitionRegistry getSchema() {
        return registry;
    }

}
