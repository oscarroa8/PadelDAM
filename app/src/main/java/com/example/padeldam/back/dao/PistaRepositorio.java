package com.example.padeldam.back.dao;

import android.util.Log;

import com.example.padeldam.back.entidades.Pista;
import com.example.padeldam.back.interfaces.IPistas;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ALL*/
public class PistaRepositorio implements IPistas<Pista> {
    private final FirebaseFirestore bd;

    private static final String TAG = PistaRepositorio.class.getName();

    public PistaRepositorio(FirebaseFirestore bd) {
        this.bd = bd;
    }

    @Override
    public Task<List<Pista>> findAll() {
        return bd.collection("pistas")
                .get()
                .continueWith( task -> {
                            List<Pista> pistas = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Pista p = new Pista(
                                            document.getId(),
                                            document.get("nombre", String.class),
                                            document.get("numero", Integer.class),
                                            document.get("material", String.class),
                                            document.get("precioHora", Double.class)

                                    );
                                    pistas.add(p);
                                }
                            }
                            else {
                                Log.w(TAG, "Error en consulta de firebase", task.getException());
                            }
                            return pistas;
                        }
                );
    }

    @Override
    public Task<String> insertar(Pista pista) {
        return bd.collection("pistas")
                .add(pista)
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
    public Task<Void> actualizar(Pista entidad) {

        return null;
    }

    @Override
    public Task<Void> borrar(Pista pista) {
        return bd.collection("pistas").document(pista.getIdPista())
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
    public Pista getById(String id) {
        return null;
    }
    @Override
    public Task<Pista> obtenerPistaPorId(String pistaId) {
        return bd.collection("pistas").document(pistaId).get().continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    return new Pista(
                            document.getId(),
                            document.getString("nombre"),
                            document.getLong("numero").intValue(),
                            document.getString("material"),
                            document.getLong("precioHora").intValue()
                    );
                } else {
                    Log.w("PistasRepositorio", "Pista no encontrada");
                    return null;
                }
            } else {
                Log.w("PistasRepositorio", "Error en consulta de firebase", task.getException());
                return null;
            }
        });
    }
}
