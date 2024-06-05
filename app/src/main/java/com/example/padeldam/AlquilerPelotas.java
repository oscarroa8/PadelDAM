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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.padeldam.back.dao.MaterialesRepositorio;
import com.example.padeldam.back.entidades.BotePelotas;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AlquilerPelotas extends AppCompatActivity {
    private GridLayout gridLayout;
    private FirebaseFirestore db;
    private MaterialesRepositorio mr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquiler_pelotas);
        gridLayout = findViewById(R.id.gridLayoutBotesPelotas);
        db = FirebaseFirestore.getInstance();
        mr = new MaterialesRepositorio(db);

        cargarBotes();
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

    private void cargarBotes() {
        mr.findAllBotes().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<BotePelotas> botesPelotas = task.getResult();
                mostrarBotesPelotas(botesPelotas);
            }
        });
    }

    private void mostrarBotesPelotas(List<BotePelotas> botesPelotas) {
        gridLayout.removeAllViews();
        final Context context = this;

        for (BotePelotas bote : botesPelotas) {

            Button button = new Button(this);
            button.setText(bote.getNombre() + "\n" + bote.getMarca() + "\n" + bote.getPrecio() + "€");

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(16, 32, 16, 16); // Establecer los márgenes

            button.setLayoutParams(params);

            if (!bote.isAlquilado()) {
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.noReservado));
            }
            if (bote.isAlquilado()) {
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.reservado));
            }

            button.setOnClickListener(view -> {
                if (bote.isAlquilado()) {
                  /*  Intent intent = new Intent(this, DetallesAlquiler.class);
                    intent.putExtra("alquiler", alquiler);
                    startActivity(intent);*/
                } else {
                    Intent i = new Intent(AlquilerPelotas.this, FormularioAlquiler.class);
                    i.putExtra("nombreMaterial", bote.getNombre());
                    i.putExtra("marca", bote.getMarca());
                    startActivity(i);
                }
            });

            gridLayout.addView(button);
        }
    }

  /*  private void mostrarDialogoAlquilar(BotePelotas bote) {
        new AlertDialog.Builder(this)
                .setTitle("Alquilar")
                .setMessage("¿Estás seguro de que deseas alquilar este bote " + bote.getNombre() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    mr.actualizarBote(bote)
                            .addOnSuccessListener(aVoid -> {
                                cargarBotes();
                                Toast.makeText(AlquilerPelotas.this, "Bote alquilado con éxito", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(AlquilerPelotas.this, "Error al alquilar el bote", Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("No", null)
                .show();
    }*/

    private void mostrarDialogoDesAlquilar(BotePelotas bote) {
        new AlertDialog.Builder(this)
                .setTitle("Devolver")
                .setMessage("¿Estás seguro de que deseas devolver este bote " + bote.getNombre() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    mr.devolverBote(bote)
                            .addOnSuccessListener(aVoid -> {
                                cargarBotes();
                                Toast.makeText(AlquilerPelotas.this, "Bote devuelto con éxito", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(AlquilerPelotas.this, "Error al devolver el bote", Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void crearPelotas(View v) {
        Intent i = new Intent(AlquilerPelotas.this, NuevoBotePelotas.class);
        startActivity(i);
    }
}

