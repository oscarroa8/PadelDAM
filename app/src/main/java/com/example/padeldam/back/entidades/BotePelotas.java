package com.example.padeldam.back.entidades;

import java.util.UUID;

/** @noinspection ALL*/
public class BotePelotas {
    private String idMaterial;
    private double precio;
    private String nombre;
    private String marca;






    public BotePelotas() {

    }

    public BotePelotas(String idMaterial, String nombre,String marca, double precio) {
        this.idMaterial = idMaterial;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;




    }
    public BotePelotas(String nombre, String marca, double precio) {
        this.idMaterial = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.marca = marca;
        this.precio =precio;



    }

    // Métodos getter y setter
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

}

