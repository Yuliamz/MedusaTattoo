package com.medusa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Sede.
 */
@Entity
@Table(name = "sede")
public class Sede implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[0-9]*$")
    @Column(name = "telefono", length = 10, nullable = false)
    private String telefono;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "sede")
    @JsonIgnore
    private Set<Trabajo> trabajos = new HashSet<>();

    @OneToMany(mappedBy = "sede")
    @JsonIgnore
    private Set<Tatuador> tatuadors = new HashSet<>();

    @OneToMany(mappedBy = "sede")
    @JsonIgnore
    private Set<Inscripcion> inscripcions = new HashSet<>();
	
	@OneToMany(mappedBy = "sede")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Sede nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Sede direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public Sede telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Sede estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Set<Trabajo> getTrabajos() {
        return trabajos;
    }

    public Sede trabajos(Set<Trabajo> trabajos) {
        this.trabajos = trabajos;
        return this;
    }

    public Sede addTrabajo(Trabajo trabajo) {
        this.trabajos.add(trabajo);
        trabajo.setSede(this);
        return this;
    }

    public Sede removeTrabajo(Trabajo trabajo) {
        this.trabajos.remove(trabajo);
        trabajo.setSede(null);
        return this;
    }

    public void setTrabajos(Set<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }

    public Set<Tatuador> getTatuadors() {
        return tatuadors;
    }

    public Sede tatuadors(Set<Tatuador> tatuadors) {
        this.tatuadors = tatuadors;
        return this;
    }

    public Sede addTatuador(Tatuador tatuador) {
        this.tatuadors.add(tatuador);
        tatuador.setSede(this);
        return this;
    }

    public Sede removeTatuador(Tatuador tatuador) {
        this.tatuadors.remove(tatuador);
        tatuador.setSede(null);
        return this;
    }

    public void setTatuadors(Set<Tatuador> tatuadors) {
        this.tatuadors = tatuadors;
    }

    public Set<Inscripcion> getInscripcions() {
        return inscripcions;
    }

    public Sede inscripcions(Set<Inscripcion> inscripcions) {
        this.inscripcions = inscripcions;
        return this;
    }

    public Sede addInscripcion(Inscripcion inscripcion) {
        this.inscripcions.add(inscripcion);
        inscripcion.setSede(this);
        return this;
    }

    public Sede removeInscripcion(Inscripcion inscripcion) {
        this.inscripcions.remove(inscripcion);
        inscripcion.setSede(null);
        return this;
    }

    public void setInscripcions(Set<Inscripcion> inscripcions) {
        this.inscripcions = inscripcions;
    }
	
	
	
	
	public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sede sede = (Sede) o;
        if (sede.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sede.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sede{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", estado='" + isEstado() + "'" +
            "}";
    }
}
