package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.SeguidoresPagina;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SeguidoresPagina entity.
 */
public interface SeguidoresPaginaSearchRepository extends ElasticsearchRepository<SeguidoresPagina, Long> {
}
