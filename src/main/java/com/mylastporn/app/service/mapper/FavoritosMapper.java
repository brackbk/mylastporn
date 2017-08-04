package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.FavoritosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Favoritos and its DTO FavoritosDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, VisibilidadeMapper.class, ModulosMapper.class, })
public interface FavoritosMapper extends EntityMapper <FavoritosDTO, Favoritos> {

    @Mapping(source = "user.id", target = "userId")

    @Mapping(source = "visibilidade.id", target = "visibilidadeId")

    @Mapping(source = "modulos.id", target = "modulosId")
    @Mapping(source = "modulos.nome", target = "modulosNome")
    FavoritosDTO toDto(Favoritos favoritos); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "visibilidadeId", target = "visibilidade")

    @Mapping(source = "modulosId", target = "modulos")
    Favoritos toEntity(FavoritosDTO favoritosDTO); 
    default Favoritos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Favoritos favoritos = new Favoritos();
        favoritos.setId(id);
        return favoritos;
    }
}
