package com.mylastporn.app.service.mapper;

import com.mylastporn.app.domain.*;
import com.mylastporn.app.service.dto.VideoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Video and its DTO VideoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TipoMapper.class, VisibilidadeMapper.class, TagsMapper.class, PaginaMapper.class, })
public interface VideoMapper extends EntityMapper <VideoDTO, Video> {

    @Mapping(source = "user.id", target = "userId")

    @Mapping(source = "tipo.id", target = "tipoId")
    @Mapping(source = "tipo.nome", target = "tipoNome")

    @Mapping(source = "visibilidade.id", target = "visibilidadeId")
    VideoDTO toDto(Video video); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "tipoId", target = "tipo")

    @Mapping(source = "visibilidadeId", target = "visibilidade")
    Video toEntity(VideoDTO videoDTO); 
    default Video fromId(Long id) {
        if (id == null) {
            return null;
        }
        Video video = new Video();
        video.setId(id);
        return video;
    }
}
