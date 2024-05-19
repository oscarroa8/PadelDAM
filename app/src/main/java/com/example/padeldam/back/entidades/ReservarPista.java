package com.example.padeldam.back.entidades;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ReservarPista implements Serializable {
        private String fecha;
        private String hora;
        private String cliente;
        private String pista;
        private FirebaseUser empleado;

        // Constructor vacío necesario para Firestore
        public ReservarPista() {
        }

        // Constructor completo
        public ReservarPista(String fecha, String hora, String cliente, String pista, FirebaseUser empleado) {
            this.fecha = fecha;
            this.hora = hora;
            this.cliente = cliente;
            this.pista = pista;
            this.empleado = empleado;
        }

        // Getters y Setters
        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getHora() {
            return hora;
        }

        public void setHora(String hora) {
            this.hora = hora;
        }

        public String getCliente() {
            return cliente;
        }

        public void setCliente(String cliente) {
            this.cliente = cliente;
        }

        public String getPista() {
            return pista;
        }

        public void setPista(String pista) {
            this.pista = pista;
        }

        public FirebaseUser getEmpleado() {
            return empleado;
        }

        public void setEmpleado(FirebaseUser empleado) {
            this.empleado = empleado;
        }
    }

