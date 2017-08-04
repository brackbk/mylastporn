package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.Modulos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Modulos entity.
 */
public interface ModulosSearchRepository extends ElasticsearchRepository<Modulos, Long> {
}
