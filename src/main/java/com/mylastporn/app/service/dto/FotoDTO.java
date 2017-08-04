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
 * A DTO for the Foto entity.
 */
public class FotoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String titulo;

    @Lob
    private String descricao;

    private Boolean gay;

    private String imagem;

    private Status status;

    private Integer visitado;

    @NotNull
    private ZonedDateTime datacriado;

    private Long userId;

    private Long tipoId;

    private String tipoNome;

    private Long visibilidadeId;

    private Set<TagsDTO> tags = new HashSet<>();

    private Set<PaginaDTO> paginas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isGay() {
        return gay;
    }

    public void setGay(Boolean gay) {
        this.gay = gay;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public String getTipoNome() {
        return tipoNome;
    }

    public void setTipoNome(String tipoNome) {
        this.tipoNome = tipoNome;
    }

    public Long getVisibilidadeId() {
        return visibilidadeId;
    }

    public void setVisibilidadeId(Long visibilidadeId) {
        this.visibilidadeId = visibilidadeId;
    }

    public Set<TagsDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagsDTO> tags) {
        this.tags = tags;
    }

    public Set<PaginaDTO> getPaginas() {
        return paginas;
    }

    public void setPaginas(Set<PaginaDTO> paginas) {
        this.paginas = paginas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FotoDTO fotoDTO = (FotoDTO) o;
        if(fotoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fotoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FotoDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", gay='" + isGay() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", status='" + getStatus() + "'" +
            ", visitado='" + getVisitado() + "'" +
            ", datacriado='" + getDatacriado() + "'" +
            "}";
    }
}
