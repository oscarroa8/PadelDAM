package com.example.padeldam.back.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Alquiler implements Serializable {

    private String idAlquiler;
    private String cliente;
    private String empleado;
    private String nombreMaterial;
    private String marca;



    public Alquiler(String idAlquiler, String cliente, String empleado, String nombreMaterial, String marca) {
        this.idAlquiler = idAlquiler;
        this.cliente = cliente;
        this.empleado = empleado;
        this.nombreMaterial = nombreMaterial;
        this.marca = marca;
    }

    public Alquiler( String cliente, String empleado, String nombreMaterial,String marca) {
        this.idAlquiler =  UUID.randomUUID().toString();
        this.cliente = cliente;
        this.empleado = empleado;
        this.nombreMaterial = nombreMaterial;
        this.marca = marca;
    }

    public Alquiler() {
    }

    public String getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(String idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
