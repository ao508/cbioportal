package org.cbioportal.persistence;

import org.cbioportal.model.CancerStudy;
import org.cbioportal.model.CancerStudyTags;
import org.cbioportal.model.meta.BaseMeta;

import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface StudyRepository {

    @Cacheable("cBioPortalRepositoryCache")
    List<CancerStudy> getAllStudies(String keyword, String projection, Integer pageSize, Integer pageNumber,
                                    String sortBy, String direction);

    BaseMeta getMetaStudies(String keyword);

    CancerStudy getStudy(String studyId, String projection);

    List<CancerStudy> fetchStudies(List<String> studyIds, String projection);
    
    BaseMeta fetchMetaStudies(List<String> studyIds);
    
    CancerStudyTags getTags(String studyId);

    List<CancerStudyTags> getTagsForMultipleStudies(List<String> studyIds);
}
