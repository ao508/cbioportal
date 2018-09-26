package org.cbioportal.service.graphql;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.cbioportal.persistence.StudyRepository;
import org.cbioportal.model.CancerStudy;

import java.util.*;

@Component
public class CancerStudyFetcher {

    @Autowired
    private StudyRepository studyRepository;

    public DataFetcher getData() {
        return environment -> {
            
            // List<CancerStudy> allStudies = studyRepository.getAllStudies(Projection.SUMMARY, PagingConstants.MAX_PAGE_SIZE, PagingConstants.DEFAULT_PAGE_NUMBER, null, Direction.ASC);
            List<CancerStudy> allStudies = studyRepository.getAllStudies("SUMMARY", 1000000, 0, null, "ASC");
            
            List<Map<String, Object>> allCancerStudies = new ArrayList<>();

            for (CancerStudy study : allStudies) {
                Map<String, Object> cancerStudy = new HashMap<>();
                cancerStudy.put("name", study.getName());
                cancerStudy.put("shortName", study.getShortName());
                allCancerStudies.add(cancerStudy);
            }
            return allCancerStudies;
        };
    }

}
