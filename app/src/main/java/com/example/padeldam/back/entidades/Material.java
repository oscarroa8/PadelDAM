package com.example.padeldam.back.entidades;

import java.io.Serializable;

public class Material implements Serializable {
    private String idMaterial;
    private double precioHora;
    private String nombre;


    public Material() {
    }

    public Material(String idMaterial, double precioHora, String nombre) {
        this.idMaterial = idMaterial;
        this.precioHora = precioHora;
        this.nombre = nombre;

    }

    public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }

    public double getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
