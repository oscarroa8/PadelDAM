package com.example.padeldam.back.dao;

import android.util.Log;

import com.example.padeldam.back.entidades.BotePelotas;
import com.example.padeldam.back.entidades.Cliente;
import com.example.padeldam.back.entidades.Material;
import com.example.padeldam.back.entidades.Palas;
import com.example.padeldam.back.entidades.Pista;
import com.example.padeldam.back.entidades.Zapatillas;
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
                                            document.get("precio", Integer.class),
                                            document.get("alquilado", boolean.class)

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

    @Override
    public Task<Void> actualizarBote(BotePelotas bote) {
        DocumentReference boteRef = bd.collection("Materiales").document("Pelotas")
                .collection("botes")
                .document(bote.getIdMaterial());

        Log.d("Firestore", "Actualizando bote: " + bote.getIdMaterial());

        return boteRef.update(
                "nombre", bote.getNombre(),
                "marca", bote.getMarca(),
                "precio", bote.getPrecio(),
                "alquilado", true
        ).addOnSuccessListener(aVoid -> {
            Log.d("Firestore", "Bote actualizado exitosamente");
        }).addOnFailureListener(e -> {
            Log.e("Firestore", "Error actualizando el bote", e);
        });
        }

    @Override
    public Task<Void> devolverBote(BotePelotas bote) {
        DocumentReference boteRef = bd.collection("Materiales").document("Pelotas")
                .collection("botes")
                .document(bote.getIdMaterial());

        Log.d("Firestore", "Actualizando bote: " + bote.getIdMaterial());

        return boteRef.update(
                "nombre", bote.getNombre(),
                "marca", bote.getMarca(),
                "precio", bote.getPrecio(),
                "alquilado", false
        ).addOnSuccessListener(aVoid -> {
            Log.d("Firestore", "Bote actualizado exitosamente");
        }).addOnFailureListener(e -> {
            Log.e("Firestore", "Error actualizando el bote", e);
        });
    }

    @Override
    public Task<List<Zapatillas>> findAllZapatillas() {
        return  bd.collection("Materiales").document("Zapatillas").collection("zapatillas")
                .get()
                .continueWith( task -> {
                            List<Zapatillas> zapatillas = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Zapatillas zapas= new Zapatillas(
                                            document.getId(),
                                            document.get("nombre", String.class),
                                            document.get("marca", String.class),
                                            document.get("precio", Integer.class),
                                            document.get("alquilado", boolean.class),
                                            document.get("talla", String.class)

                                    );
                                    zapatillas.add(zapas);
                                }
                            }
                            else {
                                Log.w(TAG, "Error en consulta de firebase", task.getException());
                            }
                            return zapatillas;
                        }
                );
    }

    @Override
    public Task<String> insertarZapatillas(Zapatillas zapas) {
        return  bd.collection("Materiales").document("Zapatillas").collection("zapatillas")
                .add(zapas)
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

    @Override
    public Task<Void> actualizarZapatillas(Zapatillas zapas) {
        DocumentReference boteRef = bd.collection("Materiales").document("Zapatillas")
                .collection("zapatillas")
                .document(zapas.getIdMaterial());

        Log.d("Firestore", "Actualizando zapas: " + zapas.getIdMaterial());

        return boteRef.update(
                "nombre", zapas.getNombre(),
                "marca", zapas.getMarca(),
                "precio", zapas.getPrecio(),
                "alquilado", true,
                "talla", zapas.getTalla()
        ).addOnSuccessListener(aVoid -> {
            Log.d("Firestore", "Bote actualizado exitosamente");
        }).addOnFailureListener(e -> {
            Log.e("Firestore", "Error actualizando el bote", e);
        });

    }

    @Override
    public Task<Void> devolverZapatillas(Zapatillas zapas) {
        DocumentReference boteRef = bd.collection("Materiales").document("Zapatillas")
                .collection("zapatillas")
                .document(zapas.getIdMaterial());

        Log.d("Firestore", "Actualizando zapas: " + zapas.getIdMaterial());

        return boteRef.update(
                "nombre", zapas.getNombre(),
                "marca", zapas.getMarca(),
                "precio", zapas.getPrecio(),
                "alquilado", false,
                "talla", zapas.getTalla()
        ).addOnSuccessListener(aVoid -> {
            Log.d("Firestore", "Bote actualizado exitosamente");
        }).addOnFailureListener(e -> {
            Log.e("Firestore", "Error actualizando el bote", e);
        });
    }

    @Override
    public Task<List<Palas>> findAllPalas() {
        return  bd.collection("Materiales").document("Palas").collection("palas")
                .get()
                .continueWith( task -> {
                            List<Palas> palas = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Palas pala= new Palas(
                                            document.getId(),
                                            document.get("nombre", String.class),
                                            document.get("marca", String.class),
                                            document.get("modelo", String.class),
                                            document.get("precio", Integer.class),
                                            document.get("alquilado", boolean.class)

                                    );
                                    palas.add(pala);
                                }
                            }
                            else {
                                Log.w(TAG, "Error en consulta de firebase", task.getException());
                            }
                            return palas;
                        }
                );
    }

    @Override
    public Task<String> insertarPalas(Palas pala) {
        return  bd.collection("Materiales").document("Palas").collection("palas")
                .add(pala)
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

    @Override
    public Task<Void> actualizarPalas(Palas pala) {
        DocumentReference boteRef = bd.collection("Materiales").document("Palas")
                .collection("palas")
                .document(pala.getIdMaterial());

        Log.d("Firestore", "Actualizando palas: " + pala.getIdMaterial());

        return boteRef.update(
                "nombre", pala.getNombre(),
                "marca", pala.getMarca(),
                "marca", pala.getMarca(),
                "precio", pala.getPrecio(),
                "alquilado", true

        ).addOnSuccessListener(aVoid -> {
            Log.d("Firestore", "Bote actualizado exitosamente");
        }).addOnFailureListener(e -> {
            Log.e("Firestore", "Error actualizando el bote", e);
        });
    }

    @Override
    public Task<Void> devolverPalas(Palas pala) {
        DocumentReference boteRef = bd.collection("Materiales").document("Palas")
                .collection("palas")
                .document(pala.getIdMaterial());

        Log.d("Firestore", "Actualizando palas: " + pala.getIdMaterial());

        return boteRef.update(
                "nombre", pala.getNombre(),
                "marca", pala.getMarca(),
                "marca", pala.getMarca(),
                "precio", pala.getPrecio(),
                "alquilado", false

        ).addOnSuccessListener(aVoid -> {
            Log.d("Firestore", "Bote actualizado exitosamente");
        }).addOnFailureListener(e -> {
            Log.e("Firestore", "Error actualizando el bote", e);
        });
    }


}



