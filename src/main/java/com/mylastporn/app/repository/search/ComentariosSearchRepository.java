package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.Comentarios;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Comentarios entity.
 */
public interface ComentariosSearchRepository extends ElasticsearchRepository<Comentarios, Long> {
}
