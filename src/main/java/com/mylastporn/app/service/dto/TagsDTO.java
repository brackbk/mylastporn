package com.mylastporn.app.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.mylastporn.app.domain.enumeration.Status;

/**
 * A DTO for the Tags entity.
 */
public class TagsDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String nome;

    private String imagem;

    private Status status;

    private Integer visitado;

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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getVisitado() {
        return visitado;
    }

    public void setVisitado(Integer visitado) {
        this.visitado = visitado;
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

        TagsDTO tagsDTO = (TagsDTO) o;
        if(tagsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tagsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TagsDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", status='" + getStatus() + "'" +
            ", visitado='" + getVisitado() + "'" +
            ", datacriado='" + getDatacriado() + "'" +
            "}";
    }
}
