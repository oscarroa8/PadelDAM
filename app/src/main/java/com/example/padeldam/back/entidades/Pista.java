package com.example.padeldam.back.entidades;

import java.io.Serializable;
import java.util.UUID;

public class Pista implements Serializable {
    private String idPista;
    private String nombre;
    private int numero;
    private String material;
    private Integer precioHora;

    public Pista() {
    }

    public Pista(String idPista, String nombre, int numero, String material, Integer precioHora) {
        this.idPista = idPista;
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

    public Integer getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(Integer precioHora) {
        this.precioHora = precioHora;
    }
}
