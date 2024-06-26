package com.example.padeldam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.padeldam.back.dao.MaterialesRepositorio;
import com.example.padeldam.back.entidades.Palas;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/** @noinspection ALL*/
public class NuevaPala extends AppCompatActivity {
    private EditText etPrecio;
    private EditText etNombre;
    private EditText etMarca;
    private EditText etModelo;
    private Button btnCrearPala;

    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_pala);

        etPrecio = findViewById(R.id.etPrecioPala);
        etNombre = findViewById(R.id.etNombre);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etMarca = findViewById(R.id.etMarca);
        etModelo = findViewById(R.id.etModelo);
        btnCrearPala = findViewById(R.id.btnCrearPala);

        db = FirebaseFirestore.getInstance();

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
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
            Toast.makeText(getApplicationContext(), "Sesion finalizada", Toast.LENGTH_SHORT).show();

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);    }

    public void insertarPala(View v) {
        String precioStr = etPrecio.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String marca = etMarca.getText().toString().trim();
        String modelo = etModelo.getText().toString().trim();

        if (precioStr.isEmpty() || nombre.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio = Double.parseDouble(precioStr);

        Palas nuevaPala = new Palas(nombre, marca, modelo, precio);
        MaterialesRepositorio mr = new MaterialesRepositorio(db);
        mr.insertarPalas(nuevaPala)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Pala creada correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, AlquilerPalas.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Error al insertar los datos de la pala", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}