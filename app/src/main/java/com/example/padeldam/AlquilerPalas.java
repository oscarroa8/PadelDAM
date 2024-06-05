package com.example.padeldam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.padeldam.back.dao.MaterialesRepositorio;
import com.example.padeldam.back.entidades.BotePelotas;
import com.example.padeldam.back.entidades.Palas;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AlquilerPalas extends AppCompatActivity {

    private GridLayout gridLayout;
    private FirebaseFirestore db;
    private MaterialesRepositorio mr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquiler_palas);
        gridLayout = findViewById(R.id.gridLayoutPalas);
        db = FirebaseFirestore.getInstance();
        mr = new MaterialesRepositorio(db);

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
                mostrarPalas(palas);
            }
        });
    }

    private void mostrarPalas(List<Palas> palas) {
        gridLayout.removeAllViews();
        final Context context = this;

        for (Palas pala : palas) {

            Button button = new Button(this);
            button.setText(pala.getNombre() + "\n" + pala.getMarca() + " " +pala.getModelo()+"\n" + pala.getPrecio() + "€");

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(16, 32, 16, 16); // Establecer los márgenes

            button.setLayoutParams(params);

            if (!pala.isAlquilado()) {
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.noReservado));
            }
            if (pala.isAlquilado()) {
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.reservado));
            }

            button.setOnClickListener(view -> {
                if (pala.isAlquilado()) {
                    mostrarDialogoDesAlquilar(pala);
                } else {
                    Intent i = new Intent(AlquilerPalas.this, FormularioAlquiler.class);
                    i.putExtra("nombreMaterial", pala.getNombre());
                    i.putExtra("marca", pala.getMarca());
                    startActivity(i);
                }
            });

            gridLayout.addView(button);
        }
    }

    private void mostrarDialogoAlquilar(Palas pala) {
        new AlertDialog.Builder(this)
                .setTitle("Alquilar")
                .setMessage("¿Estás seguro de que deseas alquilar esta pala " + pala.getMarca() +" "+ pala.getModelo()+"?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    mr.actualizarPalas(pala)
                            .addOnSuccessListener(aVoid -> {
                                cargarPalas();
                                Toast.makeText(AlquilerPalas.this, "Pala alquilada con éxito", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(AlquilerPalas.this, "Error al alquilar una pala", Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void mostrarDialogoDesAlquilar(Palas pala) {
        new AlertDialog.Builder(this)
                .setTitle("Devolver")
                .setMessage("¿Estás seguro de que deseas devolver este bote " +  pala.getMarca() +" "+ pala.getModelo()+ "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    mr.devolverPalas(pala)
                            .addOnSuccessListener(aVoid -> {
                                cargarPalas();
                                Toast.makeText(AlquilerPalas.this, "Pala devuelta con éxito", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(AlquilerPalas.this, "Error al devolver la pala", Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void crearPala(View v) {
        Intent i = new Intent(AlquilerPalas.this, NuevaPala.class);
        startActivity(i);
    }
}