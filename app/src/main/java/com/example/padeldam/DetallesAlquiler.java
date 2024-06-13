package com.example.padeldam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.padeldam.back.dao.AlquilerRepositorio;
import com.example.padeldam.back.dao.ClienteRepositorio;
import com.example.padeldam.back.dao.ReservasRepositorio;
import com.example.padeldam.back.entidades.Alquiler;
import com.example.padeldam.back.entidades.Reserva;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetallesAlquiler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalles_alquiler);
        ClienteRepositorio clientesRepositorio = new ClienteRepositorio(FirebaseFirestore.getInstance());
        Intent intent = getIntent();
        Alquiler alquiler = (Alquiler) intent.getSerializableExtra("alquiler");
        String documento = intent.getStringExtra("documento");
        String coleccion = intent.getStringExtra("coleccion");


        if (alquiler != null) {
            String idMaterial = alquiler.getIdMaterial();
            Log.d("DEBUG", "idMaterial: " + idMaterial);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference materialRef = db.collection("Materiales").document(documento).collection(coleccion).document(idMaterial);

            materialRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Obtener los datos del material
                            String nombreMaterial = document.getString("nombre");
                            String marca = document.getString("marca");

                            // Mostrar los datos del material en la interfaz de usuario
                            TextView textViewNombre = findViewById(R.id.tvNombreMaterial);
                            textViewNombre.setText("Nombre: " + nombreMaterial + " / Marca: " + marca);

                        } else {
                            Log.d("DEBUG", "Documento no existe para idMaterial: " + idMaterial);
                            Toast.makeText(DetallesAlquiler.this, "El material no existe", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("DEBUG", "Error al obtener documento: ", task.getException());
                        Toast.makeText(DetallesAlquiler.this, "Error al obtener los datos del material", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Log.d("DEBUG", "Alquiler es null");
            Toast.makeText(DetallesAlquiler.this, "Error al obtener los detalles del alquiler", Toast.LENGTH_SHORT).show();
        }


        TextView textViewCliente = findViewById(R.id.textViewCliente);
        String idCliente = alquiler.getIdCliente();
        if (idCliente != null) {
            clientesRepositorio.obtenerNombreClientePorId(idCliente).addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (task.isSuccessful()) {
                        String nombreCliente = task.getResult();
                        if (nombreCliente != null) {
                            textViewCliente.setText("Cliente: " + nombreCliente);
                        } else {
                            textViewCliente.setText("Cliente no encontrado");
                        }
                    } else {
                        textViewCliente.setText("Error al obtener el cliente");
                    }
                }
            });
        } else {
            textViewCliente.setText("Cliente no especificado");
        }


        TextView textViewEmpleado = findViewById(R.id.textViewEmpleado);
        textViewEmpleado.setText("Empleado: " + alquiler.getEmpleado());

        Button buttonCancelar = findViewById(R.id.buttonCancelarAlquiler);

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancelar la reserva
                cancelarAlquiler(alquiler);
            }
        });

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



    private void cancelarAlquiler(Alquiler alquiler) {
        AlquilerRepositorio ar = new AlquilerRepositorio(FirebaseFirestore.getInstance());
        ar.borrar(alquiler).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(DetallesAlquiler.this,Alquilar.class);
                    Toast.makeText(DetallesAlquiler.this, "Producto devuelto", Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                } else {
                    Toast.makeText(DetallesAlquiler.this, "Error al devolver el producto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}