package com.example.padeldam.back.entidades;

import java.io.Serializable;

public class Empleado implements Serializable {
    private Integer idEmpleado;
    private String nombre;
    private String mail;
    private String contrasena;

    public Empleado() {
    }

    public Empleado(Integer idEmpleado, String nombre, String mail, String contrasena) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.mail = mail;
        this.contrasena = contrasena;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
