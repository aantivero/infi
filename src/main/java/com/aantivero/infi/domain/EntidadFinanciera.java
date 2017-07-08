package com.aantivero.infi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EntidadFinanciera.
 */
@Entity
@Table(name = "entidad_financiera")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EntidadFinanciera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 5)
    @Column(name = "codigo", length = 5, nullable = false)
    private String codigo;

    @NotNull
    @Size(min = 2)
    @Column(name = "denominacion", nullable = false)
    private String denominacion;

    @NotNull
    @Column(name = "codigo_numerico", nullable = false)
    private Integer codigoNumerico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public EntidadFinanciera codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public EntidadFinanciera denominacion(String denominacion) {
        this.denominacion = denominacion;
        return this;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Integer getCodigoNumerico() {
        return codigoNumerico;
    }

    public EntidadFinanciera codigoNumerico(Integer codigoNumerico) {
        this.codigoNumerico = codigoNumerico;
        return this;
    }

    public void setCodigoNumerico(Integer codigoNumerico) {
        this.codigoNumerico = codigoNumerico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntidadFinanciera entidadFinanciera = (EntidadFinanciera) o;
        if (entidadFinanciera.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entidadFinanciera.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntidadFinanciera{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", denominacion='" + getDenominacion() + "'" +
            ", codigoNumerico='" + getCodigoNumerico() + "'" +
            "}";
    }
}
