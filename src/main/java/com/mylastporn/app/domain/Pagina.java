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
 * A Pagina.
 */
@Entity
@Table(name = "pagina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pagina")
public class Pagina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull
    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "gay")
    private Boolean gay;

    @Column(name = "capa")
    private String capa;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "official")
    private Boolean official;

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
    @JoinTable(name = "pagina_tags",
               joinColumns = @JoinColumn(name="paginas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"))
    private Set<Tags> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Pagina titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Pagina descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isGay() {
        return gay;
    }

    public Pagina gay(Boolean gay) {
        this.gay = gay;
        return this;
    }

    public void setGay(Boolean gay) {
        this.gay = gay;
    }

    public String getCapa() {
        return capa;
    }

    public Pagina capa(String capa) {
        this.capa = capa;
        return this;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public Status getStatus() {
        return status;
    }

    public Pagina status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean isOfficial() {
        return official;
    }

    public Pagina official(Boolean official) {
        this.official = official;
        return this;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public Integer getVisitado() {
        return visitado;
    }

    public Pagina visitado(Integer visitado) {
        this.visitado = visitado;
        return this;
    }

    public void setVisitado(Integer visitado) {
        this.visitado = visitado;
    }

    public ZonedDateTime getDatacriado() {
        return datacriado;
    }

    public Pagina datacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
        return this;
    }

    public void setDatacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
    }

    public User getUser() {
        return user;
    }

    public Pagina user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Visibilidade getVisibilidade() {
        return visibilidade;
    }

    public Pagina visibilidade(Visibilidade visibilidade) {
        this.visibilidade = visibilidade;
        return this;
    }

    public void setVisibilidade(Visibilidade visibilidade) {
        this.visibilidade = visibilidade;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Pagina tipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Set<Tags> getTags() {
        return tags;
    }

    public Pagina tags(Set<Tags> tags) {
        this.tags = tags;
        return this;
    }

    public Pagina addTags(Tags tags) {
        this.tags.add(tags);
        return this;
    }

    public Pagina removeTags(Tags tags) {
        this.tags.remove(tags);
        return this;
    }

    public void setTags(Set<Tags> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pagina pagina = (Pagina) o;
        if (pagina.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pagina.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pagina{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", gay='" + isGay() + "'" +
            ", capa='" + getCapa() + "'" +
            ", status='" + getStatus() + "'" +
            ", official='" + isOfficial() + "'" +
            ", visitado='" + getVisitado() + "'" +
            ", datacriado='" + getDatacriado() + "'" +
            "}";
    }
}
