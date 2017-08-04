package com.mylastporn.app.repository.search;

import com.mylastporn.app.domain.Tags;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tags entity.
 */
public interface TagsSearchRepository extends ElasticsearchRepository<Tags, Long> {
}
