package com.example.padeldam.back.entidades;

import java.io.Serializable;
import java.util.UUID;

public class Pista implements Serializable {
    private String idPista;
    private String nombre;
    private int numero;
    private String material;
    private double precioHora;

    public Pista() {
    }

    public Pista(String idPista, String nombre, int numero, String material, double precioHora) {
        this.idPista = idPista;
        this.nombre = nombre;
        this.numero = numero;
        this.material = material;
        this.precioHora = precioHora;
    }

    public Pista(String nombre, int numero, String material, double precioHora) {
        this.idPista = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.numero = numero;
        this.material = material;
        this.precioHora = precioHora;
    }

    public String getIdPista() {
        return idPista;
    }

    public void setIdPista(String idPista) {
        this.idPista = idPista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }
}
