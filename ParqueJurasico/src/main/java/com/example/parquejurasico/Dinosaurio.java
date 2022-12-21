package com.example.parquejurasico;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dinosaurio", schema = "pjurasico")
public class Dinosaurio {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_dino")
    private int idDino;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "raza")
    private String raza;
    @Basic
    @Column(name = "dieta")
    private String dieta;
    @Basic
    @Column(name = "altura")
    private Integer altura;
    @Basic
    @Column(name = "peso")
    private Integer peso;

    @Basic
    @Column(name = "vivo")
    private boolean vivo;
    @ManyToOne
    @JoinColumn(name = "id_recinto", referencedColumnName = "id_recinto", nullable = false)
    private Recinto recinto;

    public int getIdDino() {
        return idDino;
    }

    public void setIdDino(int idDino) {
        this.idDino = idDino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getDieta() {
        return dieta;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }
    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dinosaurio that = (Dinosaurio) o;
        return idDino == that.idDino && Objects.equals(nombre, that.nombre) && Objects.equals(raza, that.raza) && Objects.equals(dieta, that.dieta) && Objects.equals(altura, that.altura) && Objects.equals(peso, that.peso) && Objects.equals(vivo, that.vivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDino, nombre, raza, dieta, altura, peso, vivo);
    }

    public Recinto getRecinto() {
        return recinto;
    }

    public void setRecinto(Recinto recinto) {
        this.recinto = recinto;
    }

    public Dinosaurio(){

    }

    public Dinosaurio(Recinto recinto, String nombre, String raza, String dieta, int altura, int peso, boolean vivo){
        super();
        this.recinto = recinto;
        this.nombre = nombre;
        this.raza = raza;
        this.dieta = dieta;
        this.altura = altura;
        this.peso = peso;
        this.vivo = vivo;
    }

}
