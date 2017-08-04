package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.Pagina;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pagina entity.
 */
public interface PaginaSearchRepository extends ElasticsearchRepository<Pagina, Long> {
}
