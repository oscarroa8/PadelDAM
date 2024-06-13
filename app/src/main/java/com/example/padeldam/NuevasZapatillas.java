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
import com.example.padeldam.back.entidades.Zapatillas;
import com.google.firebase.firestore.FirebaseFirestore;

public class NuevasZapatillas extends AppCompatActivity {
    private EditText etPrecio;
    private EditText etNombre;
    private EditText etMarca;

    private EditText etTalla;

    private Button btnCrearZapatilla;

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
        btnCrearZapatilla = findViewById(R.id.btnCrearZapas);

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
    public void volverAtras(View view) {
        finish(); // Cierra la actividad actual y vuelve a la actividad anterior en la pila de actividades.
    }
    public void crearBote(View v) {
        String precioStr = etPrecio.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String marca = etMarca.getText().toString().trim();
        String talla = etTalla.getText().toString().trim();

        // Validar campos vacíos
        if (nombre.isEmpty() || marca.isEmpty() || precioStr.isEmpty() || talla.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El precio debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        Zapatillas nuevaZapatilla = new Zapatillas(nombre, marca, precio, talla);
        MaterialesRepositorio mr = new MaterialesRepositorio(db);

        mr.insertarZapatillas(nuevaZapatilla)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Zapatilla creada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, AlquilerZapatillas.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Error al insertar datos de la zapatilla", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}