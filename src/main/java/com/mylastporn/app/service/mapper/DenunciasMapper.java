package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.DenunciasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Denuncias and its DTO DenunciasDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TipoDenunciaMapper.class, ModulosMapper.class, })
public interface DenunciasMapper extends EntityMapper <DenunciasDTO, Denuncias> {

    @Mapping(source = "user.id", target = "userId")

    @Mapping(source = "tipoDenuncia.id", target = "tipoDenunciaId")
    @Mapping(source = "tipoDenuncia.tipo", target = "tipoDenunciaTipo")

    @Mapping(source = "modulos.id", target = "modulosId")
    @Mapping(source = "modulos.nome", target = "modulosNome")
    DenunciasDTO toDto(Denuncias denuncias); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "tipoDenunciaId", target = "tipoDenuncia")

    @Mapping(source = "modulosId", target = "modulos")
    Denuncias toEntity(DenunciasDTO denunciasDTO); 
    default Denuncias fromId(Long id) {
        if (id == null) {
            return null;
        }
        Denuncias denuncias = new Denuncias();
        denuncias.setId(id);
        return denuncias;
    }
}
