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
 * A Likes.
 */
@Entity
@Table(name = "likes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "likes")
public class Likes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idconteudo", nullable = false)
    private Integer idconteudo;

    @Column(name = "jhi_like")
    private Boolean like;

    @Column(name = "dislike")
    private Boolean dislike;

    @NotNull
    @Column(name = "datacriado", nullable = false)
    private ZonedDateTime datacriado;

    @ManyToOne
    private User user;

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

    public Likes idconteudo(Integer idconteudo) {
        this.idconteudo = idconteudo;
        return this;
    }

    public void setIdconteudo(Integer idconteudo) {
        this.idconteudo = idconteudo;
    }

    public Boolean isLike() {
        return like;
    }

    public Likes like(Boolean like) {
        this.like = like;
        return this;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public Boolean isDislike() {
        return dislike;
    }

    public Likes dislike(Boolean dislike) {
        this.dislike = dislike;
        return this;
    }

    public void setDislike(Boolean dislike) {
        this.dislike = dislike;
    }

    public ZonedDateTime getDatacriado() {
        return datacriado;
    }

    public Likes datacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
        return this;
    }

    public void setDatacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
    }

    public User getUser() {
        return user;
    }

    public Likes user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Modulos getModulos() {
        return modulos;
    }

    public Likes modulos(Modulos modulos) {
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
        Likes likes = (Likes) o;
        if (likes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), likes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Likes{" +
            "id=" + getId() +
            ", idconteudo='" + getIdconteudo() + "'" +
            ", like='" + isLike() + "'" +
            ", dislike='" + isDislike() + "'" +
            ", datacriado='" + getDatacriado() + "'" +
            "}";
    }
}
