package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.Favoritos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Favoritos entity.
 */
public interface FavoritosSearchRepository extends ElasticsearchRepository<Favoritos, Long> {
}
