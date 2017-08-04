package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.TipoDenunciaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoDenuncia and its DTO TipoDenunciaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDenunciaMapper extends EntityMapper <TipoDenunciaDTO, TipoDenuncia> {
    
    
    default TipoDenuncia fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDenuncia tipoDenuncia = new TipoDenuncia();
        tipoDenuncia.setId(id);
        return tipoDenuncia;
    }
}
