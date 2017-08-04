package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.ModulosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Modulos and its DTO ModulosDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModulosMapper extends EntityMapper <ModulosDTO, Modulos> {
    
    
    default Modulos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Modulos modulos = new Modulos();
        modulos.setId(id);
        return modulos;
    }
}
