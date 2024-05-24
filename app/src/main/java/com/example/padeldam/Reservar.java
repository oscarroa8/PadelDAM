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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.padeldam.back.dao.ReservasRepositorio;
import com.example.padeldam.back.entidades.Reserva;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Reservar extends AppCompatActivity {
    private TextView textViewFechaHora;
    private Spinner spinnerClientes;
    private Button buttonReservar;

    private String fechaSeleccionada;
    private String horaSeleccionada;
    private String clienteSeleccionado;

    private String nombrePista;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        textViewFechaHora = findViewById(R.id.textViewFechaHora);
        spinnerClientes = findViewById(R.id.spinnerClientes);
        buttonReservar = findViewById(R.id.buttonReservar);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Recupera los datos del intent
        Intent intent = getIntent();
        fechaSeleccionada = intent.getStringExtra("fechaSeleccionada");
        horaSeleccionada = intent.getStringExtra("horaSeleccionada");
        nombrePista = intent.getStringExtra("nombrePista");

        // Muestra la fecha y hora seleccionadas
        textViewFechaHora.setText(fechaSeleccionada + " " + horaSeleccionada);

        // Cargar clientes de Firestore y configurar el Spinner
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
                        clientes.add(nombreCliente);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Reservar.this, android.R.layout.simple_spinner_item, clientes);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerClientes.setAdapter(adapter);

                    spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            clienteSeleccionado = (String) parent.getItemAtPosition(position);
                            buttonReservar.setEnabled(true);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            buttonReservar.setEnabled(false);
                        }
                    });

                } else {
                    Toast.makeText(Reservar.this, "Error al cargar clientes", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void reservarHora(View v) {
        if (clienteSeleccionado == null) {
            Toast.makeText(Reservar.this, "Por favor, seleccione un cliente", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser empleado = mAuth.getCurrentUser();

        // Crear un nuevo objeto Reserva
        Reserva reserva = new Reserva(fechaSeleccionada, horaSeleccionada, clienteSeleccionado, nombrePista, empleado);

        ReservasRepositorio rp = new ReservasRepositorio(db);

        rp.insertar(reserva)
                .addOnCompleteListener(task -> {
            Toast.makeText(this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,FechaYHora.class);//Falta crear la clase usuarios
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
