package org.cbioportal.persistence;

import org.cbioportal.model.Gistic;
import org.cbioportal.model.GisticToGene;
import org.cbioportal.model.meta.BaseMeta;

import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface SignificantCopyNumberRegionRepository {
    
    @Cacheable("cBioPortalRepositoryCache")
    List<Gistic> getSignificantCopyNumberRegions(String studyId, String projection, Integer pageSize, 
                                                 Integer pageNumber, String sortBy, String direction);

    BaseMeta getMetaSignificantCopyNumberRegions(String studyId);

    @Cacheable("cBioPortalRepositoryCache")
    List<GisticToGene> getGenesOfRegions(List<Long> gisticRoiIds);
}
