package com.example.padeldam.back.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ReservarPista implements Serializable {
    private String idPista;
    private String idReserva;
    private String idEmpleado;
    private String idCliente;
    private Date fecha;
    private double precio;
    private int horaInicio;
    private int horaFin;


    public ReservarPista() {
    }

    public ReservarPista(String idPista, String idReserva, String idEmpleado, String idCliente, Date fecha, double precio, int horaInicio, int horaFin) {
        this.idPista = idPista;
        this.idReserva = idReserva;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.precio = precio;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public String getIdPista() {
        return idPista;
    }

    public void setIdPista(String idPista) {
        this.idPista = idPista;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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
