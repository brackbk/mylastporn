package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.AmigosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Amigos and its DTO AmigosDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface AmigosMapper extends EntityMapper <AmigosDTO, Amigos> {

    @Mapping(source = "user.id", target = "userId")
    AmigosDTO toDto(Amigos amigos); 

    @Mapping(source = "userId", target = "user")
    Amigos toEntity(AmigosDTO amigosDTO); 
    default Amigos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Amigos amigos = new Amigos();
        amigos.setId(id);
        return amigos;
    }
}
