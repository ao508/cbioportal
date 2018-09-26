package org.cbioportal.service.impl;

import graphql.ExecutionInput;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.cbioportal.persistence.GraphQLSchemaRepository;
import org.cbioportal.service.graphql.GraphQLWiring;
import org.cbioportal.service.GraphQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphQLServiceImpl implements GraphQLService {

    @Autowired
    private GraphQLSchemaRepository graphQLSchemaRepository;

    @Autowired
    private GraphQLWiring wiring;

    @Override
    public Map<String, Object> executeQuery(String query) {
        TypeDefinitionRegistry typeDefinitionRegistry = graphQLSchemaRepository.getSchema();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, wiring.buildRuntimeWiring());
        GraphQL build = GraphQL.newGraphQL(graphQLSchema).instrumentation(new TracingInstrumentation()).build();

        ExecutionInput executionInput=ExecutionInput.newExecutionInput().query(query).build();
        ExecutionResult executionResult=build.execute(executionInput);
        return executionResult.toSpecification();
    }
}
