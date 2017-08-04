package com.mylastporn.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Favoritos.
 */
@Entity
@Table(name = "favoritos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "favoritos")
public class Favoritos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idconteudo", nullable = false)
    private Integer idconteudo;

    @NotNull
    @Column(name = "datacriado", nullable = false)
    private ZonedDateTime datacriado;

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

    public Favoritos idconteudo(Integer idconteudo) {
        this.idconteudo = idconteudo;
        return this;
    }

    public void setIdconteudo(Integer idconteudo) {
        this.idconteudo = idconteudo;
    }

    public ZonedDateTime getDatacriado() {
        return datacriado;
    }

    public Favoritos datacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
        return this;
    }

    public void setDatacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
    }

    public User getUser() {
        return user;
    }

    public Favoritos user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Visibilidade getVisibilidade() {
        return visibilidade;
    }

    public Favoritos visibilidade(Visibilidade visibilidade) {
        this.visibilidade = visibilidade;
        return this;
    }

    public void setVisibilidade(Visibilidade visibilidade) {
        this.visibilidade = visibilidade;
    }

    public Modulos getModulos() {
        return modulos;
    }

    public Favoritos modulos(Modulos modulos) {
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
        Favoritos favoritos = (Favoritos) o;
        if (favoritos.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), favoritos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Favoritos{" +
            "id=" + getId() +
            ", idconteudo='" + getIdconteudo() + "'" +
            ", datacriado='" + getDatacriado() + "'" +
            "}";
    }
}
