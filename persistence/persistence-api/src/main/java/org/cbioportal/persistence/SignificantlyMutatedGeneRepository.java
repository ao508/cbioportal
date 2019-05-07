package org.cbioportal.persistence;

import org.cbioportal.model.MutSig;
import org.cbioportal.model.meta.BaseMeta;

import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface SignificantlyMutatedGeneRepository {

    @Cacheable("SignificantlyMutatedGeneDataRepositoryCache")
    List<MutSig> getSignificantlyMutatedGenes(String studyId, String projection, Integer pageSize, Integer pageNumber,
                                              String sortBy, String direction);

    BaseMeta getMetaSignificantlyMutatedGenes(String studyId);
}
