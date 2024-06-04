package com.example.padeldam.back.dao;

import android.util.Log;

import com.example.padeldam.back.entidades.Alquiler;
import com.example.padeldam.back.interfaces.IAlquiler;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AlquilerRepositorio implements IAlquiler<Alquiler> {

    private final FirebaseFirestore bd;

    private static final String TAG = AlquilerRepositorio.class.getName();


    public AlquilerRepositorio(FirebaseFirestore bd) {
        this.bd = bd;
    }


    @Override
    public Task<String> insertar(Alquiler alquiler) {
        return bd.collection("alquileres")
                .add(alquiler)
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
    public Task<Void> actualizar(Alquiler entidad) {
        return null;
    }

    @Override
    public Task<Void> borrar(Alquiler entidad) {
        return null;
    }

    @Override
    public Alquiler getById(Integer id) {
        return null;
    }
}
