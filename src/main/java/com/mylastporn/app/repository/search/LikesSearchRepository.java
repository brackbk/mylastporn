package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.Likes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Likes entity.
 */
public interface LikesSearchRepository extends ElasticsearchRepository<Likes, Long> {
}
