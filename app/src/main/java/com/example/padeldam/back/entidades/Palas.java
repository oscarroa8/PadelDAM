package com.example.padeldam.back.entidades;

import java.util.UUID;

public class Palas {

    private String idMaterial;
    private double precio;
    private String nombre;
    private String marca;
    private String modelo;

    public Palas() {
    }

    public Palas(String idMaterial, String nombre, String marca, String modelo,  double precio) {
        this.idMaterial = idMaterial;
        this.precio = precio;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;

    }

    public Palas( String nombre, String marca, String modelo,double precio) {
        this.idMaterial = UUID.randomUUID().toString();;
        this.precio = precio;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;

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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }


}
