package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.PaginaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pagina and its DTO PaginaDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, VisibilidadeMapper.class, TipoMapper.class, TagsMapper.class, })
public interface PaginaMapper extends EntityMapper <PaginaDTO, Pagina> {

    @Mapping(source = "user.id", target = "userId")

    @Mapping(source = "visibilidade.id", target = "visibilidadeId")

    @Mapping(source = "tipo.id", target = "tipoId")
    @Mapping(source = "tipo.nome", target = "tipoNome")
    PaginaDTO toDto(Pagina pagina); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "visibilidadeId", target = "visibilidade")

    @Mapping(source = "tipoId", target = "tipo")
    Pagina toEntity(PaginaDTO paginaDTO); 
    default Pagina fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pagina pagina = new Pagina();
        pagina.setId(id);
        return pagina;
    }
}
