package com.example.padeldam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.padeldam.back.dao.MaterialesRepositorio;
import com.example.padeldam.back.entidades.BotePelotas;
import com.google.firebase.firestore.FirebaseFirestore;

public class NuevoBotePelotas extends AppCompatActivity {

    private EditText etPrecioHora;
    private EditText etNombre;
    private EditText etMarca;

    private Button btnCrearBote;

    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_bote_pelotas);

        etPrecioHora = findViewById(R.id.etPrecioHora);
        etNombre = findViewById(R.id.etNombre);
        etMarca = findViewById(R.id.etMarca);
        btnCrearBote = findViewById(R.id.btnCrearBote);

        db = FirebaseFirestore.getInstance();

    }

    public void crearBote(View v) {
        String  precioStr= etPrecioHora.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String marca = etMarca.getText().toString().trim();



        double precio = Double.parseDouble(precioStr);


        BotePelotas nuevoBote = new BotePelotas(nombre,marca,precio);

        MaterialesRepositorio mr = new MaterialesRepositorio(db);

        if (!nombre.isEmpty() && !marca.isEmpty()&& !precioStr.isEmpty()) {
            mr.insertarBotePelotas(nuevoBote)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this,AlquilerPelotas.class);
                        startActivity(intent);
                    });
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        }


    }
}