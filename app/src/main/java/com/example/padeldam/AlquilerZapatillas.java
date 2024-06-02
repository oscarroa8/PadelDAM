package com.example.padeldam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.padeldam.back.dao.MaterialesRepositorio;
import com.example.padeldam.back.entidades.BotePelotas;
import com.example.padeldam.back.entidades.Zapatillas;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AlquilerZapatillas extends AppCompatActivity {
    private GridLayout gridLayout;
    private FirebaseFirestore db;
    private MaterialesRepositorio mr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquiler_zapatillas);
        gridLayout = findViewById(R.id.gridLayoutZapatillas);
        db = FirebaseFirestore.getInstance();
        mr = new MaterialesRepositorio(db);

        cargarZapas();
    }

    private void cargarZapas() {
        mr.findAllZapatillas().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<Zapatillas> zapatillas = task.getResult();
                mostrarZapatillas(zapatillas);
            }
        });
    }

    private void mostrarZapatillas(List<Zapatillas> zapatillas) {
        gridLayout.removeAllViews();
        final Context context = this;

        for (Zapatillas zapas : zapatillas) {

            Button button = new Button(this);
            button.setText(zapas.getNombre() + "\n" + zapas.getMarca() + "\n" + zapas.getPrecio() + "€"+ " talla"+ zapas.getTalla());

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(16, 32, 16, 16); // Establecer los márgenes

            button.setLayoutParams(params);

            if (!zapas.isAlquilado()) {
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.noReservado));
            }
            if (zapas.isAlquilado()) {
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.reservado));
            }

            button.setOnClickListener(view -> {
                if (zapas.isAlquilado()) {
                    mostrarDialogoDesAlquilar(zapas);
                } else {
                    mostrarDialogoAlquilar(zapas);
                }
            });

            gridLayout.addView(button);
        }
    }

    private void mostrarDialogoAlquilar(Zapatillas zapas) {
        new AlertDialog.Builder(this)
                .setTitle("Alquilar")
                .setMessage("¿Estás seguro de que deseas alquilar esta zapatillas " + zapas.getNombre() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    mr.actualizarZapatillas(zapas)
                            .addOnSuccessListener(aVoid -> {
                                cargarZapas();
                                Toast.makeText(AlquilerZapatillas.this, "Zapatillas alquiladas con éxito", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(AlquilerZapatillas.this, "Error al alquilar el zapatillas", Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void mostrarDialogoDesAlquilar(Zapatillas zapas) {
        new AlertDialog.Builder(this)
                .setTitle("Devolver")
                .setMessage("¿Estás seguro de que deseas devolver estas zapatillas " + zapas.getNombre() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    mr.devolverZapatillas(zapas)
                            .addOnSuccessListener(aVoid -> {
                                cargarZapas();
                                Toast.makeText(AlquilerZapatillas.this, "Zapatillas devueltas con éxito", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(AlquilerZapatillas.this, "Error al devolver las zapatillas", Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void crearZapatillas(View v) {
        Intent i = new Intent(AlquilerZapatillas.this, NuevasZapatillas.class);
        startActivity(i);
    }
}