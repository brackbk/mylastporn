package com.mylastporn.app.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TipoDenuncia entity.
 */
public class TipoDenunciaDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoDenunciaDTO tipoDenunciaDTO = (TipoDenunciaDTO) o;
        if(tipoDenunciaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoDenunciaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoDenunciaDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
