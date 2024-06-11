package com.example.padeldam.back.dao;

import android.util.Log;

import com.example.padeldam.back.entidades.Alquiler;
import com.example.padeldam.back.entidades.Cliente;
import com.example.padeldam.back.interfaces.IAlquiler;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
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
    public Task<Void> borrar(Alquiler alquiler) {

        return bd.collection("alquileres").document(alquiler.getIdAlquiler())
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
    public Alquiler getById(String id) {
        return null;
    }

    @Override
    public Task<List<Alquiler>> findAll() {
        return bd.collection("alquileres")
                .get()
                .continueWith( task -> {
                            List<Alquiler> alquileres = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Alquiler a= new Alquiler(
                                            document.getId(),
                                            document.get("cliente", String.class),
                                            document.get("empleado", String.class),
                                            document.get("nombreMaterial", String.class),
                                            document.get("marca", String.class)

                                    );
                                    alquileres.add(a);
                                }
                            }
                            else {
                                Log.w(TAG, "Error en consulta de firebase", task.getException());
                            }
                            return alquileres;
                        }
                );
    }
}
