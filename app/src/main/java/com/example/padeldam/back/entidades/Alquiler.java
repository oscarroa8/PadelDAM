package com.example.padeldam.back.entidades;

import java.io.Serializable;
import java.util.Date;

public class Alquiler implements Serializable {

    private Integer idAlquiler;
    private Integer idCliente;
    private Integer idMaterial;
    private Integer idEmpleado;
    private Date fecha;
    private int horaInicio;
    private int horaFin;

    public Alquiler() {
    }

    public Alquiler(Integer idAlquiler, Integer idCliente, Integer idMaterial, Integer idEmpleado, Date fecha, int horaInicio, int horaFin) {
        this.idAlquiler = idAlquiler;
        this.idCliente = idCliente;
        this.idMaterial = idMaterial;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Integer getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(Integer idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(int horaFin) {
        this.horaFin = horaFin;
    }
}
