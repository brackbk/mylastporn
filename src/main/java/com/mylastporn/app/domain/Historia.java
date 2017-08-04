package com.mylastporn.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mylastporn.app.domain.enumeration.Status;

/**
 * A Historia.
 */
@Entity
@Table(name = "historia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "historia")
public class Historia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull
    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "gay")
    private Boolean gay;

    @NotNull
    @Column(name = "imagem", nullable = false)
    private String imagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "visitado")
    private Integer visitado;

    @NotNull
    @Column(name = "datacriado", nullable = false)
    private ZonedDateTime datacriado;

    @ManyToOne
    private User user;

    @ManyToOne
    private Visibilidade visibilidade;

    @ManyToOne
    private Tipo tipo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "historia_tags",
               joinColumns = @JoinColumn(name="historias_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"))
    private Set<Tags> tags = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "historia_pagina",
               joinColumns = @JoinColumn(name="historias_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="paginas_id", referencedColumnName="id"))
    private Set<Pagina> paginas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Historia titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Historia descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isGay() {
        return gay;
    }

    public Historia gay(Boolean gay) {
        this.gay = gay;
        return this;
    }

    public void setGay(Boolean gay) {
        this.gay = gay;
    }

    public String getImagem() {
        return imagem;
    }

    public Historia imagem(String imagem) {
        this.imagem = imagem;
        return this;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Status getStatus() {
        return status;
    }

    public Historia status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getVisitado() {
        return visitado;
    }

    public Historia visitado(Integer visitado) {
        this.visitado = visitado;
        return this;
    }

    public void setVisitado(Integer visitado) {
        this.visitado = visitado;
    }

    public ZonedDateTime getDatacriado() {
        return datacriado;
    }

    public Historia datacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
        return this;
    }

    public void setDatacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
    }

    public User getUser() {
        return user;
    }

    public Historia user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Visibilidade getVisibilidade() {
        return visibilidade;
    }

    public Historia visibilidade(Visibilidade visibilidade) {
        this.visibilidade = visibilidade;
        return this;
    }

    public void setVisibilidade(Visibilidade visibilidade) {
        this.visibilidade = visibilidade;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Historia tipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Set<Tags> getTags() {
        return tags;
    }

    public Historia tags(Set<Tags> tags) {
        this.tags = tags;
        return this;
    }

    public Historia addTags(Tags tags) {
        this.tags.add(tags);
        return this;
    }

    public Historia removeTags(Tags tags) {
        this.tags.remove(tags);
        return this;
    }

    public void setTags(Set<Tags> tags) {
        this.tags = tags;
    }

    public Set<Pagina> getPaginas() {
        return paginas;
    }

    public Historia paginas(Set<Pagina> paginas) {
        this.paginas = paginas;
        return this;
    }

    public Historia addPagina(Pagina pagina) {
        this.paginas.add(pagina);
        return this;
    }

    public Historia removePagina(Pagina pagina) {
        this.paginas.remove(pagina);
        return this;
    }

    public void setPaginas(Set<Pagina> paginas) {
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
        Historia historia = (Historia) o;
        if (historia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Historia{" +
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
