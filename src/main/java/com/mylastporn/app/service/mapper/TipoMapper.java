package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.TipoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tipo and its DTO TipoDTO.
 */
@Mapper(componentModel = "spring", uses = {ModulosMapper.class, })
public interface TipoMapper extends EntityMapper <TipoDTO, Tipo> {

    @Mapping(source = "modulos.id", target = "modulosId")
    @Mapping(source = "modulos.nome", target = "modulosNome")
    TipoDTO toDto(Tipo tipo); 

    @Mapping(source = "modulosId", target = "modulos")
    Tipo toEntity(TipoDTO tipoDTO); 
    default Tipo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tipo tipo = new Tipo();
        tipo.setId(id);
        return tipo;
    }
}
