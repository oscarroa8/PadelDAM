package com.example.padeldam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.padeldam.adaptadores.AdapterClientes;
import com.example.padeldam.adaptadores.ListAdapterPistas;
import com.example.padeldam.back.dao.ClienteRepositorio;
import com.example.padeldam.back.dao.PistaRepositorio;
import com.example.padeldam.back.entidades.Pista;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Pistas extends AppCompatActivity {
    private ListView listaPistas;
    ListAdapterPistas adaptador;
    ListAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pistas);
        listaPistas = findViewById(R.id.listaPistas);

        FirebaseFirestore bd = FirebaseFirestore.getInstance();
        PistaRepositorio pr = new PistaRepositorio(bd);
        adaptador = new ListAdapterPistas(Pistas.this,R.layout.card_pista, new ArrayList<>());
        listaPistas.setAdapter(adaptador);
        pr.findAll().addOnCompleteListener(task -> {
            adaptador.setPistas(task.getResult());
        });

        adapter= listaPistas.getAdapter();

    }

    public void crearPista (View v){
        Intent i = new Intent(Pistas.this, nuevaPista.class);
        startActivity(i);
    }


    public void volverPistas (View v){
        Intent i = new Intent(Pistas.this, Menu.class);
        startActivity(i);
    }
}