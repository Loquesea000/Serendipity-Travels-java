package com.SerendipityTravels.app.Entity;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "paquetes")
public class PaquetesTuristicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paquete")
    private int id;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;
    
    @NotNull
    @Column(name = "precioBase")
    private double precioBase;


    @OneToMany(mappedBy = "paquete", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reservas> reservas;
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }
}
