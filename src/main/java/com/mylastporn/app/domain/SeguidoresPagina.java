package com.mylastporn.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.mylastporn.app.domain.enumeration.Status;

/**
 * A SeguidoresPagina.
 */
@Entity
@Table(name = "seguidores_pagina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "seguidorespagina")
public class SeguidoresPagina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    private Pagina pagina;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public SeguidoresPagina status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public SeguidoresPagina pagina(Pagina pagina) {
        this.pagina = pagina;
        return this;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }

    public User getUser() {
        return user;
    }

    public SeguidoresPagina user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeguidoresPagina seguidoresPagina = (SeguidoresPagina) o;
        if (seguidoresPagina.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seguidoresPagina.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeguidoresPagina{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
