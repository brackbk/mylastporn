package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.Denuncias;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Denuncias entity.
 */
public interface DenunciasSearchRepository extends ElasticsearchRepository<Denuncias, Long> {
}
