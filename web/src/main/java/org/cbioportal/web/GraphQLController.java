package org.cbioportal.web;

import org.cbioportal.web.config.annotation.PublicApi;
import org.cbioportal.service.GraphQLService;

import java.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

@PublicApi
@Api(tags = "Test")
@RestController
public class GraphQLController {

    @Autowired
    private GraphQLService graphQLService; 
    
    @RequestMapping(value = "/graphql", method = RequestMethod.POST)
    @ApiOperation("Please work")
    public Map<String, Object> myGraphql(@RequestBody Map<String, Object> request) throws Exception {
        System.out.println("STARTING");
        System.out.println(request);

        JSONObject jsonRequest = new JSONObject(request);
        
        System.out.println(jsonRequest);
        System.out.println(jsonRequest.getString("query"));
        return graphQLService.executeQuery(jsonRequest.getString("query"));
    }
}
