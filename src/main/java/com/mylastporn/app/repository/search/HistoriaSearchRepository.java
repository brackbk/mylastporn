package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.Historia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Historia entity.
 */
public interface HistoriaSearchRepository extends ElasticsearchRepository<Historia, Long> {
}
