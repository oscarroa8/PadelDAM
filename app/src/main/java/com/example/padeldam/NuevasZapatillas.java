package com.example.padeldam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.padeldam.back.dao.MaterialesRepositorio;
import com.example.padeldam.back.entidades.Zapatillas;
import com.google.firebase.firestore.FirebaseFirestore;

public class NuevasZapatillas extends AppCompatActivity {
    private EditText etPrecio;
    private EditText etNombre;
    private EditText etMarca;

    private EditText etTalla;

    private Button btnCrearBote;

    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevas_zapatillas);

        etPrecio = findViewById(R.id.etPrecioZapas);
        etNombre = findViewById(R.id.etNombre);
        etMarca = findViewById(R.id.etMarca);
        etTalla = findViewById(R.id.etTalla);
        btnCrearBote = findViewById(R.id.btnCrearZapas);

        db = FirebaseFirestore.getInstance();

    }

    public void crearBote(View v) {
        String  precioStr= etPrecio.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String marca = etMarca.getText().toString().trim();
        String talla = etTalla.getText().toString().trim();
        double precio = Double.parseDouble(precioStr);


        Zapatillas nuevaZapatilla = new Zapatillas(nombre,marca,precio,talla);

        MaterialesRepositorio mr = new MaterialesRepositorio(db);

        if (!nombre.isEmpty() && !marca.isEmpty()&& !precioStr.isEmpty()) {
            mr.insertarZapatillas(nuevaZapatilla)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this,AlquilerZapatillas.class);
                        startActivity(intent);
                    });
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        }


    }
}