package com.example.padeldam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.padeldam.adaptadores.AdapterClientes;
import com.example.padeldam.back.dao.ClienteRepositorio;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Clientes extends AppCompatActivity {

    private ListView listaClientes;
    AdapterClientes adaptador;
    ListAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clientes);
        listaClientes = findViewById(R.id.listViewClientes);

        FirebaseFirestore bd = FirebaseFirestore.getInstance();
        ClienteRepositorio cr = new ClienteRepositorio(bd);
        adaptador = new AdapterClientes(Clientes.this,R.layout.card_cliente, new ArrayList<>());
        listaClientes.setAdapter(adaptador);
        cr.findAll().addOnCompleteListener(task -> {
            adaptador.setClientes(task.getResult());
        });

        adapter= listaClientes.getAdapter();

    }

    public void crearCliente (View v){
        Intent i = new Intent(Clientes.this, nuevoCliente.class);
        startActivity(i);
    }


}