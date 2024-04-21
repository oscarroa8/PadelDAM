package com.example.padeldam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.padeldam.back.dao.PistaRepositorio;
import com.example.padeldam.back.entidades.Pista;
import com.google.firebase.firestore.FirebaseFirestore;

public class nuevaPista extends AppCompatActivity {
    Button btnCrear;
    EditText etNombre,etNumero,etMaterial,etPrecio;
    FirebaseFirestore bd;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nueva_pista);

        btnCrear = findViewById(R.id.btnCrearP);

        bd = FirebaseFirestore.getInstance();


        etNombre = findViewById(R.id.editTextNombrePista);
        etNumero = findViewById(R.id.etNumero);
        etMaterial = findViewById(R.id.etMaterialPista);
        etPrecio= findViewById(R.id.etNumero);
    }

    public void insertarPista(View view){
        PistaRepositorio pr = new PistaRepositorio(bd);
        String nombrePista= etNombre.getText().toString();
        int numeroPista = Integer.parseInt(etNumero.getText().toString());
        String materialPista = etMaterial.getText().toString();
       int precioPista =Integer.parseInt(etPrecio.getText().toString());

        Pista p = new Pista(nombrePista,numeroPista,materialPista,precioPista);

        if (!nombrePista.isEmpty() && !materialPista.isEmpty()) {
            pr.insertar(p)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this,Pistas.class);//Falta crear la clase usuarios
                        startActivity(intent);
                    });
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}