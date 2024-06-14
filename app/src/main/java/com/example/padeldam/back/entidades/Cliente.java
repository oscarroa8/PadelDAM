package com.example.padeldam.back.entidades;

import java.io.Serializable;
import java.util.UUID;

/** @noinspection ALL*/
public class Cliente implements Serializable {

    private String idCliente;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String telefono;
    private String mail;

    public Cliente(String idCliente, String nombre, String apellido1, String apellido2, String telefono, String mail) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.telefono = telefono;
        this.mail = mail;
    }

    public Cliente() {
    }

    public Cliente(String nombreCliente, String primerApellido, String segundoApellido, String telefono, String mail) {
        this.idCliente = UUID.randomUUID().toString();
        this.nombre = nombreCliente;
        this.apellido1 = primerApellido;
        this.apellido2 = segundoApellido;
        this.telefono = telefono;
        this.mail = mail;
    }


    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
