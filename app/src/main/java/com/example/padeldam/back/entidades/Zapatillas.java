package com.example.padeldam.back.entidades;

import java.util.UUID;

/** @noinspection ALL*/
public class Zapatillas {
    private String idMaterial;
    private double precio;
    private String nombre;
    private String marca;
    private String talla;





    public Zapatillas() {
    }

    public Zapatillas(String idMaterial,String nombre, String marca, double precio,  String talla) {
        this.idMaterial = idMaterial;
        this.precio = precio;
        this.nombre = nombre;
        this.marca = marca;
        this.talla = talla;
    }

    public Zapatillas(  String nombre, String marca, double precio,String talla) {
        this.idMaterial =  UUID.randomUUID().toString();;
        this.precio = precio;
        this.nombre = nombre;
        this.marca = marca;
        this.talla = talla;
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



    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

}
