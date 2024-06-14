package com.example.padeldam.back.entidades;

import java.io.Serializable;
import java.util.UUID;

public class Alquiler implements Serializable {

    private String idAlquiler;
    private String idCliente;
    private String empleado;

    private String idMaterial;

    public Alquiler(String idAlquiler, String idCliente, String empleado,String idMaterial) {
        this.idAlquiler = idAlquiler;
        this.idCliente = idCliente;
        this.empleado = empleado;
        this.idMaterial = idMaterial;
    }

    public Alquiler( String idCliente, String empleado,String idMaterial) {
        this.idAlquiler =  UUID.randomUUID().toString();
        this.idCliente = idCliente;
        this.empleado = empleado;
        this.idMaterial = idMaterial;
    }

    public Alquiler() {
    }

    public String getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(String idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }
}
