package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.Amigos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Amigos entity.
 */
public interface AmigosSearchRepository extends ElasticsearchRepository<Amigos, Long> {
}
