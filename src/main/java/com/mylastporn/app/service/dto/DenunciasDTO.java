package com.mylastporn.app.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.mylastporn.app.domain.enumeration.Status;

/**
 * A DTO for the Denuncias entity.
 */
public class DenunciasDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idconteudo;

    @NotNull
    @Size(min = 3)
    private String titulo;

    @NotNull
    @Lob
    private String descricao;

    private Status status;

    @NotNull
    private String email;

    private Long userId;

    private Long tipoDenunciaId;

    private String tipoDenunciaTipo;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTipoDenunciaId() {
        return tipoDenunciaId;
    }

    public void setTipoDenunciaId(Long tipoDenunciaId) {
        this.tipoDenunciaId = tipoDenunciaId;
    }

    public String getTipoDenunciaTipo() {
        return tipoDenunciaTipo;
    }

    public void setTipoDenunciaTipo(String tipoDenunciaTipo) {
        this.tipoDenunciaTipo = tipoDenunciaTipo;
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

        DenunciasDTO denunciasDTO = (DenunciasDTO) o;
        if(denunciasDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), denunciasDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DenunciasDTO{" +
            "id=" + getId() +
            ", idconteudo='" + getIdconteudo() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", status='" + getStatus() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
