package com.example.padeldam;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.padeldam.back.dao.AlquilerRepositorio;
import com.example.padeldam.back.dao.ReservasRepositorio;
import com.example.padeldam.back.entidades.Alquiler;
import com.example.padeldam.back.entidades.Reserva;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetallesAlquiler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalles_alquiler);
        // Obtener detalles de la reserva del Intent
        Intent intent = getIntent();
        Alquiler alquiler = (Alquiler) intent.getSerializableExtra("alquiler");


        // Mostrar detalles de la reserva en la interfaz de usuario
        TextView textViewNombre = findViewById(R.id.tvNombreMaterial);
        textViewNombre.setText("Nombre: " + alquiler.getNombreMaterial()+"  Marca: "+alquiler.getMarca());


        TextView textViewCliente = findViewById(R.id.textViewCliente);
        textViewCliente.setText("Cliente: " + alquiler.getCliente());

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