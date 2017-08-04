package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.VisibilidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Visibilidade and its DTO VisibilidadeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VisibilidadeMapper extends EntityMapper <VisibilidadeDTO, Visibilidade> {
    
    
    default Visibilidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Visibilidade visibilidade = new Visibilidade();
        visibilidade.setId(id);
        return visibilidade;
    }
}
