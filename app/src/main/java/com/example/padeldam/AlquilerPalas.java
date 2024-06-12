package com.example.padeldam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.padeldam.back.dao.AlquilerRepositorio;
import com.example.padeldam.back.dao.MaterialesRepositorio;
import com.example.padeldam.back.entidades.Alquiler;
import com.example.padeldam.back.entidades.BotePelotas;
import com.example.padeldam.back.entidades.Palas;
import com.example.padeldam.back.entidades.Zapatillas;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AlquilerPalas extends AppCompatActivity {

    private GridLayout gridLayout;
    private FirebaseFirestore db;
    private MaterialesRepositorio mr;

    private AlquilerRepositorio ar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquiler_palas);
        gridLayout = findViewById(R.id.gridLayoutPalas);
        db = FirebaseFirestore.getInstance();
        mr = new MaterialesRepositorio(db);
        ar = new AlquilerRepositorio(db);

        cargarPalas();
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
            Intent intent = new Intent(this,Login.class);//Falta crear la clase usuarios
            Toast.makeText(getApplicationContext(), "Usuario deslogueado", Toast.LENGTH_SHORT).show();

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);    }

    private void cargarPalas() {
        mr.findAllPalas().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<Palas> palas = task.getResult();
                ar.findAll().addOnCompleteListener(alquilerTask -> {
                    if (alquilerTask.isSuccessful() && alquilerTask.getResult() != null) {
                        List<Alquiler> alquileres = alquilerTask.getResult();
                        mostrarPalas(palas, alquileres);
                    }
                });
            }
        });
    }


    private void mostrarPalas(List<Palas> palas,List<Alquiler> alquileres) {
        gridLayout.removeAllViews();
        final Context context = this;

        for (Palas pala : palas) {

            Button button = new Button(this);
            button.setText(String.format("%s\n%s\n %s\n%.2f€", pala.getNombre(), pala.getMarca(), pala.getModelo(),pala.getPrecio()));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(16, 32, 16, 16); // Establecer los márgenes

            button.setLayoutParams(params);

            boolean isAlquilado = false;
            Alquiler alquilerEncontrado = null;
            for (Alquiler alquiler : alquileres) {
                if (alquiler.getIdMaterial() != null && pala.getIdMaterial() != null && alquiler.getIdMaterial().equals(pala.getIdMaterial())) {
                    isAlquilado = true;
                    alquilerEncontrado = alquiler;
                    break;
                }
            }

            if (!isAlquilado) {
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.noReservado));
                button.setTag("NO_ALQUILADA");

            }
            else{
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.reservado));
                button.setTag("ALQUILADA");

            }
            Alquiler finalAlquilerEncontrado = alquilerEncontrado; // Necesario para la referencia dentro del listener
            button.setOnClickListener(view -> {
                if ("ALQUILADA".equals(view.getTag())) {
                    Intent intent = new Intent(AlquilerPalas.this, DetallesAlquiler.class);
                    intent.putExtra("alquiler", finalAlquilerEncontrado);
                    intent.putExtra("documento","Palas");
                    intent.putExtra("coleccion","palas");

                    startActivity(intent);
                } else {
                    Intent i = new Intent(AlquilerPalas.this, FormularioAlquiler.class);
                    i.putExtra("idMaterial", pala.getIdMaterial());
                    i.putExtra("documento","Palas");
                    i.putExtra("coleccion","palas");
                    startActivity(i);
                }
            });

            gridLayout.addView(button);
        }
    }

    public void volverAtras(View view) {
        finish(); // Cierra la actividad actual y vuelve a la actividad anterior en la pila de actividades.
    }


    public void crearPala(View v) {
        Intent i = new Intent(AlquilerPalas.this, NuevaPala.class);
        startActivity(i);
    }
}