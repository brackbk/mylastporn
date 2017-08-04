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
 * A Foto.
 */
@Entity
@Table(name = "foto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "foto")
public class Foto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "gay")
    private Boolean gay;

    @Column(name = "imagem")
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
    private Tipo tipo;

    @ManyToOne
    private Visibilidade visibilidade;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "foto_tags",
               joinColumns = @JoinColumn(name="fotos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"))
    private Set<Tags> tags = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "foto_pagina",
               joinColumns = @JoinColumn(name="fotos_id", referencedColumnName="id"),
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

    public Foto titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Foto descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isGay() {
        return gay;
    }

    public Foto gay(Boolean gay) {
        this.gay = gay;
        return this;
    }

    public void setGay(Boolean gay) {
        this.gay = gay;
    }

    public String getImagem() {
        return imagem;
    }

    public Foto imagem(String imagem) {
        this.imagem = imagem;
        return this;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Status getStatus() {
        return status;
    }

    public Foto status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getVisitado() {
        return visitado;
    }

    public Foto visitado(Integer visitado) {
        this.visitado = visitado;
        return this;
    }

    public void setVisitado(Integer visitado) {
        this.visitado = visitado;
    }

    public ZonedDateTime getDatacriado() {
        return datacriado;
    }

    public Foto datacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
        return this;
    }

    public void setDatacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
    }

    public User getUser() {
        return user;
    }

    public Foto user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Foto tipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Visibilidade getVisibilidade() {
        return visibilidade;
    }

    public Foto visibilidade(Visibilidade visibilidade) {
        this.visibilidade = visibilidade;
        return this;
    }

    public void setVisibilidade(Visibilidade visibilidade) {
        this.visibilidade = visibilidade;
    }

    public Set<Tags> getTags() {
        return tags;
    }

    public Foto tags(Set<Tags> tags) {
        this.tags = tags;
        return this;
    }

    public Foto addTags(Tags tags) {
        this.tags.add(tags);
        return this;
    }

    public Foto removeTags(Tags tags) {
        this.tags.remove(tags);
        return this;
    }

    public void setTags(Set<Tags> tags) {
        this.tags = tags;
    }

    public Set<Pagina> getPaginas() {
        return paginas;
    }

    public Foto paginas(Set<Pagina> paginas) {
        this.paginas = paginas;
        return this;
    }

    public Foto addPagina(Pagina pagina) {
        this.paginas.add(pagina);
        return this;
    }

    public Foto removePagina(Pagina pagina) {
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
        Foto foto = (Foto) o;
        if (foto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Foto{" +
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
