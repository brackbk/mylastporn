package com.mylastporn.app.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.mylastporn.app.domain.enumeration.Status;

/**
 * A DTO for the SeguidoresPagina entity.
 */
public class SeguidoresPaginaDTO implements Serializable {

    private Long id;

    private Status status;

    private Long paginaId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getPaginaId() {
        return paginaId;
    }

    public void setPaginaId(Long paginaId) {
        this.paginaId = paginaId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeguidoresPaginaDTO seguidoresPaginaDTO = (SeguidoresPaginaDTO) o;
        if(seguidoresPaginaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seguidoresPaginaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeguidoresPaginaDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
