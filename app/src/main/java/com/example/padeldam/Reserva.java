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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.padeldam.back.entidades.ReservarPista;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Reserva extends AppCompatActivity {
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

        buttonReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservarHora();
            }
        });
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Reserva.this, android.R.layout.simple_spinner_item, clientes);
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
                    Toast.makeText(Reserva.this, "Error al cargar clientes", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void reservarHora() {
        if (clienteSeleccionado == null) {
            Toast.makeText(Reserva.this, "Por favor, seleccione un cliente", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser empleado = mAuth.getCurrentUser();

        // Crear un nuevo objeto Reserva
        ReservarPista reserva = new ReservarPista(fechaSeleccionada, horaSeleccionada, clienteSeleccionado, nombrePista, empleado);

        // Guardar la reserva en Firestore
        db.collection("reservas")
                .add(reserva)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Reserva.this, "Reserva realizada correctamente", Toast.LENGTH_SHORT).show();

                        // Regresa a la actividad anterior o a la principal
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Reserva.this, "Error al realizar la reserva", Toast.LENGTH_SHORT).show();
                    }
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
