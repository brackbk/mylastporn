package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.TipoDenuncia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoDenuncia entity.
 */
public interface TipoDenunciaSearchRepository extends ElasticsearchRepository<TipoDenuncia, Long> {
}
