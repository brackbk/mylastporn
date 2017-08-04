package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.LikesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Likes and its DTO LikesDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ModulosMapper.class, })
public interface LikesMapper extends EntityMapper <LikesDTO, Likes> {

    @Mapping(source = "user.id", target = "userId")

    @Mapping(source = "modulos.id", target = "modulosId")
    @Mapping(source = "modulos.nome", target = "modulosNome")
    LikesDTO toDto(Likes likes); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "modulosId", target = "modulos")
    Likes toEntity(LikesDTO likesDTO); 
    default Likes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Likes likes = new Likes();
        likes.setId(id);
        return likes;
    }
}
