package com.example.padeldam.back.dao;

import android.util.Log;

import com.example.padeldam.back.entidades.BotePelotas;
import com.example.padeldam.back.entidades.Cliente;
import com.example.padeldam.back.entidades.Material;
import com.example.padeldam.back.entidades.Pista;
import com.example.padeldam.back.interfaces.ICliente;
import com.example.padeldam.back.interfaces.IMateriales;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MaterialesRepositorio implements IMateriales<Material> {
    private final FirebaseFirestore bd;

    private static final String TAG = MaterialesRepositorio.class.getName();

    public MaterialesRepositorio(FirebaseFirestore bd) {
        this.bd = bd;
    }
    @Override
    public Task<String> insertar(Material entidad) {
        return null;
    }

    @Override
    public Task<Void> actualizar(Material entidad) {
        return null;
    }

    @Override
    public Task<Void> borrar(Material entidad) {
        return null;
    }

    @Override
    public Material getById(Integer id) {
        return null;
    }

    @Override
    public Task<List<BotePelotas>> findAllBotes() {
        return  bd.collection("Materiales").document("Pelotas").collection("botes")
                .get()
                .continueWith( task -> {
                            List<BotePelotas> botes = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    BotePelotas bp= new BotePelotas(
                                            document.getId(),
                                            document.get("nombre", String.class),
                                            document.get("marca", String.class),
                                            document.get("precio", Integer.class)

                                    );
                                    botes.add(bp);
                                }
                            }
                            else {
                                Log.w(TAG, "Error en consulta de firebase", task.getException());
                            }
                            return botes;
                        }
                );
    }

    @Override
    public Task<String> insertarBotePelotas(BotePelotas bote) {
       return  bd.collection("Materiales").document("Pelotas").collection("botes")
                .add(bote)
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        DocumentReference doc = task.getResult();
                        return doc.getId();
                    } else {
                        Log.w(TAG, "Error en consulta de firebase", task.getException());
                        return null;
                    }
                });
    }

}
