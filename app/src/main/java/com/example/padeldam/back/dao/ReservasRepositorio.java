package com.example.padeldam.back.dao;

import android.util.Log;

import com.example.padeldam.Reservar;
import com.example.padeldam.back.entidades.Pista;
import com.example.padeldam.back.entidades.Reserva;
import com.example.padeldam.back.interfaces.IReserva;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReservasRepositorio implements IReserva<Reserva> {
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
    public Task<Void> actualizar(Reserva entidad) {
        return null;
    }

    @Override
    public Task<Void> borrar(Reserva reserva) {
        return bd.collection("reservas").document(reserva.getIdReserva())
                .delete()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        return null;
                    }
                    else {
                        Log.w(TAG, "Error en consulta de firebase", task.getException());
                        return null;
                    }
                });
    }

    @Override
    public Reserva getById(Integer id) {
        return null;
    }

    @Override
    public Task<List<Reserva>> findAll() {
        return bd.collection("reservas")
                .get()
                .continueWith( task -> {
                            List<Reserva> reservas = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Reserva r = new Reserva(
                                            document.getId(),
                                            document.getString("fecha"),
                                            document.getString("hora"),
                                            document.getString("cliente"),
                                            document.getString("pista"),
                                            document.getString("empleadoEmail")
                                    );
                                    reservas.add(r);
                                }
                            }
                            else {
                                Log.w(TAG, "Error en consulta de firebase", task.getException());
                            }
                            return reservas;
                        }
                );
    }
}
