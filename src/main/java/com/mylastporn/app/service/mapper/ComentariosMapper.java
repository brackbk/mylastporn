package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.ComentariosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comentarios and its DTO ComentariosDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, VisibilidadeMapper.class, ModulosMapper.class, })
public interface ComentariosMapper extends EntityMapper <ComentariosDTO, Comentarios> {

    @Mapping(source = "user.id", target = "userId")

    @Mapping(source = "visibilidade.id", target = "visibilidadeId")

    @Mapping(source = "modulos.id", target = "modulosId")
    @Mapping(source = "modulos.nome", target = "modulosNome")
    ComentariosDTO toDto(Comentarios comentarios); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "visibilidadeId", target = "visibilidade")

    @Mapping(source = "modulosId", target = "modulos")
    Comentarios toEntity(ComentariosDTO comentariosDTO); 
    default Comentarios fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comentarios comentarios = new Comentarios();
        comentarios.setId(id);
        return comentarios;
    }
}
