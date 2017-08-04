package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.Foto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Foto entity.
 */
public interface FotoSearchRepository extends ElasticsearchRepository<Foto, Long> {
}
