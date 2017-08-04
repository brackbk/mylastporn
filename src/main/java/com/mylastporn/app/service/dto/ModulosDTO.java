package com.mylastporn.app.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.mylastporn.app.domain.enumeration.Status;

/**
 * A DTO for the Modulos entity.
 */
public class ModulosDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String caminho;

    private Status status;

    @NotNull
    private ZonedDateTime datacriado;

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

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModulosDTO modulosDTO = (ModulosDTO) o;
        if(modulosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modulosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModulosDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", caminho='" + getCaminho() + "'" +
            ", status='" + getStatus() + "'" +
            ", datacriado='" + getDatacriado() + "'" +
            "}";
    }
}
