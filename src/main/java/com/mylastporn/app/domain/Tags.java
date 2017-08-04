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
 * A Tags.
 */
@Entity
@Table(name = "tags")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tags")
public class Tags implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "nome", nullable = false)
    private String nome;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Tags nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public Tags imagem(String imagem) {
        this.imagem = imagem;
        return this;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Status getStatus() {
        return status;
    }

    public Tags status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getVisitado() {
        return visitado;
    }

    public Tags visitado(Integer visitado) {
        this.visitado = visitado;
        return this;
    }

    public void setVisitado(Integer visitado) {
        this.visitado = visitado;
    }

    public ZonedDateTime getDatacriado() {
        return datacriado;
    }

    public Tags datacriado(ZonedDateTime datacriado) {
        this.datacriado = datacriado;
        return this;
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
        Tags tags = (Tags) o;
        if (tags.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tags.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tags{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", status='" + getStatus() + "'" +
            ", visitado='" + getVisitado() + "'" +
            ", datacriado='" + getDatacriado() + "'" +
            "}";
    }
}
