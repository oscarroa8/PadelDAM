package com.example.padeldam.back.entidades;

import java.io.Serializable;

public class Material implements Serializable {
    private Integer idMaterial;
    private double precioHora;
    private String nombre;
    private int cantidad;

    public Material() {
    }

    public Material(Integer idMaterial, double precioHora, String nombre, int cantidad) {
        this.idMaterial = idMaterial;
        this.precioHora = precioHora;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
