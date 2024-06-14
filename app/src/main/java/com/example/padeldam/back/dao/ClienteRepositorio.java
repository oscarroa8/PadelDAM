package com.example.padeldam.back.dao;

import android.util.Log;

import com.example.padeldam.back.entidades.Cliente;
import com.example.padeldam.back.interfaces.ICliente;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepositorio implements ICliente<Cliente> {

    private final FirebaseFirestore bd;

    private static final String TAG = ClienteRepositorio.class.getName();

    public ClienteRepositorio(FirebaseFirestore bd) {
        this.bd = bd;
    }
    @Override
    public Task<List<Cliente>> findAll() {
        return bd.collection("clientes")
                .get()
                .continueWith( task -> {
                            List<Cliente> clientes = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Cliente c = new Cliente(
                                            document.getId(),
                                            document.get("nombre", String.class),
                                            document.get("apellido1", String.class),
                                            document.get("apellido2", String.class),
                                            document.get("telefono", String.class),
                                            document.get("mail", String.class)

                                    );
                                    clientes.add(c);
                                }
                            }
                            else {
                                Log.w(TAG, "Error en consulta de firebase", task.getException());
                            }
                            return clientes;
                        }
                );
    }

    @Override
    public Task<String> insertar(Cliente cliente) {
        return bd.collection("clientes")
                .add(cliente)
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
    public Task<Void> actualizar(Cliente cliente) {
        DocumentReference clienteRef = bd.collection("clientes").document(cliente.getIdCliente());
        return clienteRef.update(
                "nombre", cliente.getNombre(),
                "apellido1",cliente.getApellido1(),
                "apellido2",cliente.getApellido2(),
                "telefono",cliente.getTelefono(),
                "mail", cliente.getMail()



        );
    }

    @Override
    public Task<Void> borrar(Cliente cliente) {


        return bd.collection("clientes").document(cliente.getIdCliente())
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
    public Cliente getById(String id) {
        return null;
    }

    @Override
    public Task<String> obtenerNombreClientePorId(String clienteId) {
        return bd.collection("clientes").document(clienteId).get().continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String nombre = document.getString("nombre");
                    String primerApellido = document.getString("apellido1");
                    String segundoApellido = document.getString("apellido2");
                    return nombre + " " + primerApellido + " " + segundoApellido;
                } else {
                    Log.w("ClientesRepositorio", "Cliente no encontrado");
                    return null;
                }
            } else {
                Log.w("ClientesRepositorio", "Error en consulta de firebase", task.getException());
                return null;
            }
        });
    }
}
