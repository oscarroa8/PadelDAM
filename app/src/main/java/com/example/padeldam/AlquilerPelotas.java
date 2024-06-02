package com.example.padeldam;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.padeldam.back.dao.MaterialesRepositorio;
import com.example.padeldam.back.entidades.BotePelotas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    private void cargarBotes() {
        mr.findAllBotes().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<BotePelotas> botesPelotas = task.getResult();
                mostrarBotesPelotas(botesPelotas);
            }
        });
    }

    private void mostrarBotesPelotas(List<BotePelotas> botesPelotas) {
        // Limpiar el GridLayout antes de agregar los botones
        gridLayout.removeAllViews();

        // Crear y agregar botones para cada bote de pelotas
        for (BotePelotas bote : botesPelotas) {
            Button button = new Button(this);
            button.setText(bote.getNombre() + "\n" + bote.getMarca()+"\n" + bote.getPrecio());
            button.setId(bote.getIdMaterial().hashCode());

            // Configurar el OnClickListener para el botón
            button.setOnClickListener(view -> {
                // Lógica para alquilar este bote de pelotas
                Toast.makeText(this, "Botón de " + bote.getNombre() + " clickeado", Toast.LENGTH_SHORT).show();
            });

            // Agregar el botón al GridLayout
            gridLayout.addView(button);
        }
    }
    public void crearPelotas (View v){
        Intent i = new Intent(AlquilerPelotas.this, NuevoBotePelotas.class);
        startActivity(i);
    }

}