package org.cbioportal.service;

import java.util.*;

public interface GraphQLService {
   
    public Map<String, Object> executeQuery(String query);

}
