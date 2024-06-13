package com.example.padeldam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.padeldam.adaptadores.ListAdapterPistas;
import com.example.padeldam.back.dao.PistaRepositorio;
import com.example.padeldam.back.entidades.Pista;
import com.google.firebase.auth.FirebaseAuth;
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

        listaPistas.setOnItemClickListener((adapterView, view, position, l) -> {
            Log.d("ListViewClick", "Item clicked at position: " + position);
            Pista pistaSeleccionada = adaptador.getItem(position);

            // Crea un intent para la nueva actividad
            Intent intent = new Intent(Pistas.this, FechaYHora.class);

            // Añade los datos del ítem seleccionado al intent
            intent.putExtra("idPista", pistaSeleccionada.getIdPista());


            // Inicia la nueva actividad
            startActivity(intent);
        });


    }


    public void crearPista (View v){
        Intent i = new Intent(Pistas.this, nuevaPista.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.itemCliente){
            Intent intent = new Intent(this,Clientes.class);//Falta crear la clase usuarios
            startActivity(intent);
        }
        if(id == R.id.itemHome){
            Intent intent = new Intent(this,menuPrincipal.class);//Falta crear la clase usuarios
            startActivity(intent);
        }
        if(id == R.id.itemLogout){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent intent = new Intent(this,Login.class);//Falta crear la clase usuarios
            Toast.makeText(getApplicationContext(), "Usuario deslogueado", Toast.LENGTH_SHORT).show();

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);    }


}