package com.mylastporn.app.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.mylastporn.app.domain.enumeration.Status;

/**
 * A DTO for the Tipo entity.
 */
public class TipoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private Status status;

    @NotNull
    private ZonedDateTime datacriado;

    private Long modulosId;

    private String modulosNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ZonedDateTime getDatacriado() {
        return datacriado;
    }

    public void setDatacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
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

        TipoDTO tipoDTO = (TipoDTO) o;
        if(tipoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", status='" + getStatus() + "'" +
            ", datacriado='" + getDatacriado() + "'" +
            "}";
    }
}
