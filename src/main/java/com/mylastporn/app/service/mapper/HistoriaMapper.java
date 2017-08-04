package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.HistoriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Historia and its DTO HistoriaDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, VisibilidadeMapper.class, TipoMapper.class, TagsMapper.class, PaginaMapper.class, })
public interface HistoriaMapper extends EntityMapper <HistoriaDTO, Historia> {

    @Mapping(source = "user.id", target = "userId")

    @Mapping(source = "visibilidade.id", target = "visibilidadeId")

    @Mapping(source = "tipo.id", target = "tipoId")
    @Mapping(source = "tipo.nome", target = "tipoNome")
    HistoriaDTO toDto(Historia historia); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "visibilidadeId", target = "visibilidade")

    @Mapping(source = "tipoId", target = "tipo")
    Historia toEntity(HistoriaDTO historiaDTO); 
    default Historia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Historia historia = new Historia();
        historia.setId(id);
        return historia;
    }
}
