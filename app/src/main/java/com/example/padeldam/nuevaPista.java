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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/** @noinspection ALL*/
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnCrear = findViewById(R.id.btnCrearP);

        bd = FirebaseFirestore.getInstance();


        etNombre = findViewById(R.id.editTextNombrePista);
        etNumero = findViewById(R.id.etNumero);
        etMaterial = findViewById(R.id.etMaterialPista);
        etPrecio= findViewById(R.id.etPrecio);
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

    public void insertarPista(View view){
        PistaRepositorio pr = new PistaRepositorio(bd);
        String nombrePista = etNombre.getText().toString();
        String numeroPistaStr = etNumero.getText().toString();
        String materialPista = etMaterial.getText().toString();
        String precioPistaStr = etPrecio.getText().toString();

        // Validar campos vacíos
        if (nombrePista.isEmpty() || numeroPistaStr.isEmpty() || materialPista.isEmpty() || precioPistaStr.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convertir a los tipos correspondientes
        int numeroPista;
        double precioPista;
        try {
            numeroPista = Integer.parseInt(numeroPistaStr);
            precioPista = Double.parseDouble(precioPistaStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Número de pista y precio deben ser numéricos", Toast.LENGTH_SHORT).show();
            return;
        }
        Pista p = new Pista(nombrePista,numeroPista,materialPista,precioPista);

            pr.insertar(p)
                .addOnCompleteListener(task -> {
                    Toast.makeText(this, "Pista creada correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,Pistas.class);//Falta crear la clase usuarios
                    startActivity(intent);
                });

    }


}