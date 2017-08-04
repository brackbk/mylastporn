package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.FotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Foto and its DTO FotoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TipoMapper.class, VisibilidadeMapper.class, TagsMapper.class, PaginaMapper.class, })
public interface FotoMapper extends EntityMapper <FotoDTO, Foto> {

    @Mapping(source = "user.id", target = "userId")

    @Mapping(source = "tipo.id", target = "tipoId")
    @Mapping(source = "tipo.nome", target = "tipoNome")

    @Mapping(source = "visibilidade.id", target = "visibilidadeId")
    FotoDTO toDto(Foto foto); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "tipoId", target = "tipo")

    @Mapping(source = "visibilidadeId", target = "visibilidade")
    Foto toEntity(FotoDTO fotoDTO); 
    default Foto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Foto foto = new Foto();
        foto.setId(id);
        return foto;
    }
}
