package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.TagsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tags and its DTO TagsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagsMapper extends EntityMapper <TagsDTO, Tags> {
    
    
    default Tags fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tags tags = new Tags();
        tags.setId(id);
        return tags;
    }
}
