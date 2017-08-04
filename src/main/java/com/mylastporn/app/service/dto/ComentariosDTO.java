package com.mylastporn.app.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.mylastporn.app.domain.enumeration.Status;

/**
 * A DTO for the Comentarios entity.
 */
public class ComentariosDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idconteudo;

    @NotNull
    @Size(min = 3)
    private String titulo;

    @NotNull
    @Lob
    private String comentario;

    @NotNull
    private ZonedDateTime datacriado;

    private Status status;

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ZonedDateTime getDatacriado() {
        return datacriado;
    }

    public void setDatacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

        ComentariosDTO comentariosDTO = (ComentariosDTO) o;
        if(comentariosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comentariosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComentariosDTO{" +
            "id=" + getId() +
            ", idconteudo='" + getIdconteudo() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", datacriado='" + getDatacriado() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
