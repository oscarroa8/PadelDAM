package com.example.padeldam;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.padeldam.back.dao.AlquilerRepositorio;
import com.example.padeldam.back.dao.ReservasRepositorio;
import com.example.padeldam.back.entidades.Alquiler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FormularioAlquiler extends AppCompatActivity {
    private TextView textViewNombreMaterial;
    private Spinner spinnerClientes;
    private Button buttonAlquiler;

    private String nombreMaterial;

    private String clienteSeleccionado;


    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_alquiler);

        textViewNombreMaterial = findViewById(R.id.textViewMaterial);
        spinnerClientes = findViewById(R.id.spinnerClientes);
        buttonAlquiler = findViewById(R.id.buttonAlquilar);

        db = FirebaseFirestore.getInstance();

        // Recupera los datos del intent
        Intent intent = getIntent();
        nombreMaterial = intent.getStringExtra("nombreMaterial");

        cargarClientesEnSpinner();

    }

    private void cargarClientesEnSpinner() {
        db.collection("clientes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> clientes = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String nombreCliente = document.getString("nombre");
                        String primerApellido = document.getString("apellido1");
                        String segundoApellido = document.getString("apellido2");

                        // Construir el nombre completo del cliente
                        String nombreCompleto = nombreCliente + " " + primerApellido + " " + segundoApellido;
                        clientes.add(nombreCompleto);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(FormularioAlquiler.this, android.R.layout.simple_spinner_item, clientes);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerClientes.setAdapter(adapter);

                    spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            clienteSeleccionado = (String) parent.getItemAtPosition(position);
                            buttonAlquiler.setEnabled(true);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            buttonAlquiler.setEnabled(false);
                        }
                    });

                } else {
                    Toast.makeText(FormularioAlquiler.this, "Error al cargar clientes", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AlquilarBote(View v) {
        if (clienteSeleccionado == null) {
            Toast.makeText(FormularioAlquiler.this, "Por favor, seleccione un cliente", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser empleado = mAuth.getCurrentUser();

        // Crear un nuevo objeto Reserva
        Alquiler alquiler = new Alquiler(clienteSeleccionado, empleado.getEmail(), nombreMaterial );

        AlquilerRepositorio ar = new AlquilerRepositorio(db);

        ar.insertar(alquiler)
                .addOnCompleteListener(task -> {
                    Toast.makeText(this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Alquilar.class);
                    startActivity(intent);

                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow,menu);
        return super.onCreateOptionsMenu(menu);
    }

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
}