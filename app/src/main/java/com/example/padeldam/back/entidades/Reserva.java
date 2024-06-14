package com.example.padeldam.back.entidades;

import java.io.Serializable;
import java.util.UUID;

/** @noinspection ALL*/
public class Reserva implements Serializable {



    private String idReserva;


    private String idPista;
        private String fecha;
        private String hora;
        private String idCliente;
        private String empleadoEmail;


        public Reserva() {
        }

        // Constructor completo
        public Reserva(String idPista,String fecha, String hora, String idCliente, String empleadoEmail) {
            this.idReserva = UUID.randomUUID().toString();
            this.idPista = idPista;
            this.fecha = fecha;
            this.hora = hora;
            this.idCliente = idCliente;

            this.empleadoEmail = empleadoEmail;
        }

    public Reserva(String idReserva,String idPista,String fecha, String hora, String idCliente,  String empleadoEmail) {
        this.idReserva = idReserva;
        this.idPista = idPista;
        this.fecha = fecha;
        this.hora = hora;
        this.idCliente = idCliente;

        this.empleadoEmail = empleadoEmail;
    }



    // Getters y Setters

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }
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

        public String getIdCliente() {
            return idCliente;
        }

        public void setIdCliente(String idCliente) {
            this.idCliente = idCliente;
        }

 

        public String getEmpleadoEmail() {
            return empleadoEmail;
        }

        public void setEmpleadoEmail(String empleadoEmail) {
            this.empleadoEmail = empleadoEmail;
        }

    public String getIdPista() {
        return idPista;
    }

    public void setIdPista(String idPista) {
        this.idPista = idPista;
    }

}

