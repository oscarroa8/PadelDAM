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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

    public void volverAtras(View view) {
        finish(); // Cierra la actividad actual y vuelve a la actividad anterior en la pila de actividades.
    }

}