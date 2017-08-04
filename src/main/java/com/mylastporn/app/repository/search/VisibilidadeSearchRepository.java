package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.Visibilidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Visibilidade entity.
 */
public interface VisibilidadeSearchRepository extends ElasticsearchRepository<Visibilidade, Long> {
}
