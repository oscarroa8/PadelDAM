package com.example.padeldam.back.entidades;

import java.io.Serializable;

public class Material implements Serializable {
    private String idMaterial;
    private double precio;
    private String nombre;


    public Material() {
    }

    public Material(String idMaterial, double precioHora, String nombre) {
        this.idMaterial = idMaterial;
        this.precio = precioHora;
        this.nombre = nombre;

    }

    public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
