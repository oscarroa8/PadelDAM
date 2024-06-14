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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.padeldam.back.dao.AdminRepositorio;
import com.example.padeldam.back.dao.AlquilerRepositorio;
import com.example.padeldam.back.dao.MaterialesRepositorio;
import com.example.padeldam.back.dao.ReservasRepositorio;
import com.example.padeldam.back.entidades.Alquiler;
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
import java.util.Locale;

public class FormularioAlquiler extends AppCompatActivity {
    private TextView textViewNombreMaterial;
    private Spinner spinnerClientes;
    private Button buttonAlquiler;
    private ImageView buttonBorrarMaterial;

    private TextView textViewMarca;
    private TextView textViewPrecio;
    private String nombreMaterial;
    private String marca;
    private String documento;
    private String coleccion;
    private String clienteSeleccionado;
    private double precio;
    private String idMaterial;
    List<String> clienteNombres = new ArrayList<>();
    List<String> clienteIds = new ArrayList<>();

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_alquiler);

        textViewNombreMaterial = findViewById(R.id.tvNombreMaterial);
        textViewMarca = findViewById(R.id.tvMarcaMaterial);
        textViewPrecio = findViewById(R.id.tvPrecioMaterial);
        spinnerClientes = findViewById(R.id.spinnerClientes);
        buttonAlquiler = findViewById(R.id.buttonAlquilar);
        buttonBorrarMaterial = findViewById(R.id.borrarMaterialesAlquiler);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        idMaterial = intent.getStringExtra("idMaterial");
        documento = intent.getStringExtra("documento");
        coleccion = intent.getStringExtra("coleccion");

        if (idMaterial != null && documento != null && coleccion != null) {
            Log.d("debug",idMaterial);
            obtenerDetallesMaterial(idMaterial);
        }

        cargarClientesEnSpinner();


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser empleado = mAuth.getCurrentUser();
        buttonBorrarMaterial = findViewById(R.id.borrarMaterialesAlquiler);
        buttonBorrarMaterial.setVisibility(View.GONE);



        AdminRepositorio adminRepositorio = new AdminRepositorio(db);
        adminRepositorio.isAdmin(empleado.getEmail()).addOnCompleteListener(task -> {
            boolean admin = task.getResult();
            if (admin) {
                buttonBorrarMaterial.setVisibility(View.VISIBLE);

            }
        });


        buttonBorrarMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialesRepositorio materialRepositorio = new MaterialesRepositorio(db);
                materialRepositorio.borrarMaterial(idMaterial, documento, coleccion)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(FormularioAlquiler.this, "Material borrado exitosamente", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(FormularioAlquiler.this,Alquilar.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(FormularioAlquiler.this, "Error al borrar el material", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void obtenerDetallesMaterial(String idMaterial) {
        DocumentReference materialRef = db.collection("Materiales").document(documento).collection(coleccion).document(idMaterial);

        materialRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        actualizarTextViews(document);
                    } else {
                        Log.d("DEBUG", "No such document");
                        Toast.makeText(FormularioAlquiler.this, "El material no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("DEBUG", "get failed with ", task.getException());
                    Toast.makeText(FormularioAlquiler.this, "Error al obtener los datos del material", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void actualizarTextViews(DocumentSnapshot document) {
        String nombreMaterial = document.getString("nombre");
        String marca = document.getString("marca");
        Double precio = document.getDouble("precio");

        if ("Palas".equals(documento)) {
            String modelo = document.getString("modelo");
            textViewMarca.setText(String.format("Marca: %s / Modelo: %s", marca, modelo));
        } else if ("Zapatillas".equals(documento)) {
            String talla = document.getString("talla");
            textViewMarca.setText(String.format("Marca: %s / Talla: %s", marca, talla));
        } else if ("Pelotas".equals(documento)) {
            textViewMarca.setText(String.format("Marca: %s", marca));
        }

        textViewNombreMaterial.setText(String.format("Nombre: %s", nombreMaterial));
        textViewPrecio.setText(String.format(Locale.getDefault(), "Precio: %.2f€", precio));
    }

    private void cargarClientesEnSpinner() {
        db.collection("clientes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String idCliente = document.getId();
                        String nombreCliente = document.getString("nombre");
                        String primerApellido = document.getString("apellido1");
                        String segundoApellido = document.getString("apellido2");

                        String nombreCompleto = nombreCliente + " " + primerApellido + " " + segundoApellido;
                        clienteNombres.add(nombreCompleto);
                        clienteIds.add(idCliente);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(FormularioAlquiler.this, android.R.layout.simple_spinner_item, clienteNombres);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerClientes.setAdapter(adapter);

                    spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            clienteSeleccionado = clienteNombres.get(position);
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

        if (empleado != null) {
            String idClienteSeleccionado = clienteIds.get(spinnerClientes.getSelectedItemPosition());

            Alquiler alquiler = new Alquiler(idClienteSeleccionado, empleado.getEmail(), idMaterial);
            AlquilerRepositorio ar = new AlquilerRepositorio(db);

            ar.insertar(alquiler).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FormularioAlquiler.this, Alquilar.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Error al insertar los datos", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Error: no se encontró el usuario empleado", Toast.LENGTH_SHORT).show();
        }
    }

    public void volverAtras(View view) {
        finish(); // Cierra la actividad actual y vuelve a la actividad anterior en la pila de actividades.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemCliente) {
            Intent intent = new Intent(this, Clientes.class);
            startActivity(intent);
        } else if (id == R.id.itemHome) {
            Intent intent = new Intent(this, menuPrincipal.class);
            startActivity(intent);
        } else if (id == R.id.itemLogout) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent intent = new Intent(this, Login.class);
            Toast.makeText(getApplicationContext(), "Usuario deslogueado", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
