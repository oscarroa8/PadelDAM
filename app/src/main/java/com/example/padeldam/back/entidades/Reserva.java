package com.example.padeldam.back.entidades;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Reserva implements Serializable {
        private String fecha;
        private String hora;
        private String cliente;
        private String pista;
        private String empleadoEmail;

        // Constructor vacío necesario para Firestore
        public Reserva() {
        }

        // Constructor completo
        public Reserva(String fecha, String hora, String cliente, String pista, String empleadoEmail) {
            this.fecha = fecha;
            this.hora = hora;
            this.cliente = cliente;
            this.pista = pista;
            this.empleadoEmail = empleadoEmail;
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

        public String getEmpleadoEmail() {
            return empleadoEmail;
        }

        public void setEmpleadoEmail(String empleadoEmail) {
            this.empleadoEmail = empleadoEmail;
        }
    }

