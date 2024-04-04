package com.example.padeldam.back.entidades;

import java.io.Serializable;

public class Rol implements Serializable {
    private Integer idRol;
    private Enum rol;

    public Rol() {
    }

    public Rol(Enum rol) {
        this.rol = rol;
    }

    public Rol(Integer idRol, Enum rol) {
        this.idRol = idRol;
        this.rol = rol;
    }

    public Enum getRol() {
        return rol;
    }

    public void setRol(Enum rol) {
        this.rol = rol;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
}
