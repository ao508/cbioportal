package org.cbioportal.web;


import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.ExecutionInput;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;
import java.util.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cbioportal.model.CancerStudy;
import org.cbioportal.service.StudyService;
import org.cbioportal.service.exception.StudyNotFoundException;
import org.cbioportal.web.config.annotation.PublicApi;
import org.cbioportal.web.parameter.Direction;
import org.cbioportal.web.parameter.HeaderKeyConstants;
import org.cbioportal.web.parameter.PagingConstants;
import org.cbioportal.web.parameter.Projection;
import org.cbioportal.web.parameter.sort.StudySortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.MediaType.*;
import org.json.JSONObject;

@PublicApi
@Api(tags = "Test")
@RestController
public class GraphQLController {

    @RequestMapping(value = "/graphql", method = RequestMethod.POST)
    @ApiOperation("Please work")
    public Map<String, Object> myGraphql(@RequestBody String request) throws Exception {
         String schema = "type Query{hello: String}";

        SchemaParser schemaParser = new SchemaParser();
        JSONObject jsonRequest = new JSONObject(request);
       
        System.out.println("HERE I AM");
        System.out.println(jsonRequest);
 
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .type("Query", builder -> builder.dataFetcher("foo", new StaticDataFetcher("bar")))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        //GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionInput executionInput = ExecutionInput.newExecutionInput().query(jsonRequest.getString("query")).build();
        ExecutionResult executionResult = build.execute(executionInput);
        //ExecutionResult executionResult = build.execute("{hello}");
        
        System.out.println(executionResult.toSpecification());
        return executionResult.toSpecification();
    }
    /*
        // Prints: {hello=world}
        //     }
        String schema = "type Query{hello: Object}";
        JSONObject jsonRequest = new JSONObject(request);
        
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();

        ExecutionInput executionInput = ExecutionInput.newExecutionInput().query(jsonRequest.getString("query")).build();
        ExecutionResult executionResult = build.execute(executionInput);
        // ExecutionResult executionResult = build.execute("{hello}");
        return executionResult.toSpecification();
    }
    */
}
