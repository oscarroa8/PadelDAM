package com.example.padeldam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ALL*/
public class Reservar extends AppCompatActivity {
    private Spinner spinnerClientes;
    private Button buttonReservar;

    private String fechaSeleccionada;
    private String horaSeleccionada;
    private String clienteSeleccionado;
    private TextView tvPrecioReserva;
    private TextView tvHoraReserva;
    private TextView tvNombrePista;

    private String nombrePista;
    private String idPista;
    private String idCliente;

    private FirebaseFirestore db;

    List<String> clienteNombres = new ArrayList<>();
    List<String> clienteIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_reserva);

        spinnerClientes = findViewById(R.id.spinnerClientes);
        buttonReservar = findViewById(R.id.buttonReservar);

        tvPrecioReserva = findViewById(R.id.tvPrecioReserva);
        tvNombrePista = findViewById(R.id.tvNombrePistaReserva);
        tvHoraReserva = findViewById(R.id.tvHoraReserva);



        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Recupera los datos del intent
        Intent intent = getIntent();
        idPista = intent.getStringExtra("idPista");
        fechaSeleccionada = intent.getStringExtra("fechaSeleccionada");
        horaSeleccionada = intent.getStringExtra("horaSeleccionada");



        DocumentReference docRef = db.collection("pistas").document(idPista);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String nombrePista = document.getString("nombre");
                        Double precioPista = document.getDouble("precioHora");
                        String precioPistaStr = String.valueOf(precioPista);
                        tvNombrePista.setText(nombrePista);
                        tvPrecioReserva.setText(precioPistaStr+"€");
                        tvHoraReserva.setText(fechaSeleccionada+" / "+horaSeleccionada);
                    } else {
                        Log.d("Firestore", "Pista no encontrada");
                    }
                }
            }
        });

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
                        String idCliente = document.getId();
                        String nombreCliente = document.getString("nombre");
                        String primerApellido = document.getString("apellido1");
                        String segundoApellido = document.getString("apellido2");

                        // Construir el nombre completo del cliente
                        String nombreCompleto =nombreCliente + " " + primerApellido + " " + segundoApellido;
                        clientes.add(nombreCompleto);

                        clienteNombres.add(nombreCompleto);
                        clienteIds.add(idCliente);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Reservar.this, android.R.layout.simple_spinner_item, clientes);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerClientes.setAdapter(adapter);

                    spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            clienteSeleccionado = clienteNombres.get(position);
                            // Obtener el ID del cliente seleccionado utilizando la misma posición en la lista de IDs
                            String idClienteSeleccionado = clienteIds.get(position);
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

        String idClienteSeleccionado = clienteIds.get(spinnerClientes.getSelectedItemPosition());


        // Crear un nuevo objeto Reserva
        Reserva reserva = new Reserva(idPista,fechaSeleccionada, horaSeleccionada, idClienteSeleccionado,empleado.getEmail());

        ReservasRepositorio rp = new ReservasRepositorio(db);

        rp.insertar(reserva)
                .addOnCompleteListener(task -> {
            Toast.makeText(this, "Reserva creada correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,FechaYHora.class);
                    intent.putExtra("idPista", idPista);
                    intent.putExtra("fechaSeleccionada", fechaSeleccionada);

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


}
