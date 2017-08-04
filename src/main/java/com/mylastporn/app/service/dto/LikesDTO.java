package com.mylastporn.app.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Likes entity.
 */
public class LikesDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idconteudo;

    private Boolean like;

    private Boolean dislike;

    @NotNull
    private ZonedDateTime datacriado;

    private Long userId;

    private Long modulosId;

    private String modulosNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdconteudo() {
        return idconteudo;
    }

    public void setIdconteudo(Integer idconteudo) {
        this.idconteudo = idconteudo;
    }

    public Boolean isLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public Boolean isDislike() {
        return dislike;
    }

    public void setDislike(Boolean dislike) {
        this.dislike = dislike;
    }

    public ZonedDateTime getDatacriado() {
        return datacriado;
    }

    public void setDatacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getModulosId() {
        return modulosId;
    }

    public void setModulosId(Long modulosId) {
        this.modulosId = modulosId;
    }

    public String getModulosNome() {
        return modulosNome;
    }

    public void setModulosNome(String modulosNome) {
        this.modulosNome = modulosNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LikesDTO likesDTO = (LikesDTO) o;
        if(likesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), likesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LikesDTO{" +
            "id=" + getId() +
            ", idconteudo='" + getIdconteudo() + "'" +
            ", like='" + isLike() + "'" +
            ", dislike='" + isDislike() + "'" +
            ", datacriado='" + getDatacriado() + "'" +
            "}";
    }
}
