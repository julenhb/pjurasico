package com.example.parquejurasico;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recinto", schema = "pjurasico")
public class Recinto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_recinto")
    private int idRecinto;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "num_dinos")
    private Integer numDinos;

    @OneToMany(mappedBy="idDino",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Dinosaurio> dinosaurios;

    public int getIdRecinto() {
        return idRecinto;
    }

    public void setIdRecinto(int idRecinto) {
        this.idRecinto = idRecinto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumDinos() {
        return numDinos;
    }

    public void setNumDinos(Integer numDinos) {
        this.numDinos = numDinos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recinto that = (Recinto) o;
        return idRecinto == that.idRecinto && Objects.equals(nombre, that.nombre) && Objects.equals(numDinos, that.numDinos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRecinto, nombre, numDinos);
    }
}
