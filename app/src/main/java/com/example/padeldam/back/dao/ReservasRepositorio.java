package com.example.padeldam.back.dao;

import android.util.Log;

import com.example.padeldam.Reservar;
import com.example.padeldam.back.entidades.Reserva;
import com.example.padeldam.back.interfaces.IReserva;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReservasRepositorio implements IReserva<Reservar> {
    private final FirebaseFirestore bd;

    private static final String TAG = ReservasRepositorio.class.getName();


    public ReservasRepositorio(FirebaseFirestore bd) {
        this.bd = bd;
    }

    @Override
    public Task<String> insertar(Reserva reserva) {
        return bd.collection("reservas")
                .add(reserva)
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        DocumentReference doc = task.getResult();
                        return doc.getId();
                    }
                    else {
                        Log.w(TAG, "Error en consulta de firebase", task.getException());
                        return null;
                    }
                });
    }

    @Override
    public Task<String> insertar(Reservar entidad) {
        return null;
    }

    @Override
    public Task<Void> actualizar(Reservar entidad) {
        return null;
    }

    @Override
    public Task<Void> borrar(Reservar entidad) {
        return null;
    }

    @Override
    public Reservar getById(Integer id) {
        return null;
    }
}
