package com.mylastporn.app.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Favoritos entity.
 */
public class FavoritosDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idconteudo;

    @NotNull
    private ZonedDateTime datacriado;

    private Long userId;

    private Long visibilidadeId;

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

    public Long getVisibilidadeId() {
        return visibilidadeId;
    }

    public void setVisibilidadeId(Long visibilidadeId) {
        this.visibilidadeId = visibilidadeId;
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

        FavoritosDTO favoritosDTO = (FavoritosDTO) o;
        if(favoritosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), favoritosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FavoritosDTO{" +
            "id=" + getId() +
            ", idconteudo='" + getIdconteudo() + "'" +
            ", datacriado='" + getDatacriado() + "'" +
            "}";
    }
}
