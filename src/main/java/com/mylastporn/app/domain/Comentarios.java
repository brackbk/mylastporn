package com.mylastporn.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.mylastporn.app.domain.enumeration.Status;

/**
 * A Comentarios.
 */
@Entity
@Table(name = "comentarios")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "comentarios")
public class Comentarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idconteudo", nullable = false)
    private Integer idconteudo;

    @NotNull
    @Size(min = 3)
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull
    @Lob
    @Column(name = "comentario", nullable = false)
    private String comentario;

    @NotNull
    @Column(name = "datacriado", nullable = false)
    private ZonedDateTime datacriado;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Visibilidade visibilidade;

    @ManyToOne
    private Modulos modulos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdconteudo() {
        return idconteudo;
    }

    public Comentarios idconteudo(Integer idconteudo) {
        this.idconteudo = idconteudo;
        return this;
    }

    public void setIdconteudo(Integer idconteudo) {
        this.idconteudo = idconteudo;
    }

    public String getTitulo() {
        return titulo;
    }

    public Comentarios titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public Comentarios comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ZonedDateTime getDatacriado() {
        return datacriado;
    }

    public Comentarios datacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
        return this;
    }

    public void setDatacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
    }

    public Status getStatus() {
        return status;
    }

    public Comentarios status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public Comentarios user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Visibilidade getVisibilidade() {
        return visibilidade;
    }

    public Comentarios visibilidade(Visibilidade visibilidade) {
        this.visibilidade = visibilidade;
        return this;
    }

    public void setVisibilidade(Visibilidade visibilidade) {
        this.visibilidade = visibilidade;
    }

    public Modulos getModulos() {
        return modulos;
    }

    public Comentarios modulos(Modulos modulos) {
        this.modulos = modulos;
        return this;
    }

    public void setModulos(Modulos modulos) {
        this.modulos = modulos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comentarios comentarios = (Comentarios) o;
        if (comentarios.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comentarios.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comentarios{" +
            "id=" + getId() +
            ", idconteudo='" + getIdconteudo() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", datacriado='" + getDatacriado() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
