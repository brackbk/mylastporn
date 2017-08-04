package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.SeguidoresPaginaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SeguidoresPagina and its DTO SeguidoresPaginaDTO.
 */
@Mapper(componentModel = "spring", uses = {PaginaMapper.class, UserMapper.class, })
public interface SeguidoresPaginaMapper extends EntityMapper <SeguidoresPaginaDTO, SeguidoresPagina> {

    @Mapping(source = "pagina.id", target = "paginaId")

    @Mapping(source = "user.id", target = "userId")
    SeguidoresPaginaDTO toDto(SeguidoresPagina seguidoresPagina); 

    @Mapping(source = "paginaId", target = "pagina")

    @Mapping(source = "userId", target = "user")
    SeguidoresPagina toEntity(SeguidoresPaginaDTO seguidoresPaginaDTO); 
    default SeguidoresPagina fromId(Long id) {
        if (id == null) {
            return null;
        }
        SeguidoresPagina seguidoresPagina = new SeguidoresPagina();
        seguidoresPagina.setId(id);
        return seguidoresPagina;
    }
}
