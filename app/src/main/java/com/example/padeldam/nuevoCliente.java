package com.example.padeldam;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.padeldam.back.dao.ClienteRepositorio;
import com.example.padeldam.back.entidades.Cliente;
import com.google.firebase.firestore.FirebaseFirestore;

public class nuevoCliente extends AppCompatActivity {
    Button btnCrear;
    EditText etNombre,etPrimerApellido,etSegundoApellido,etTelefono,etMail;
    FirebaseFirestore bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nuevo_cliente);
        btnCrear = findViewById(R.id.btnGuardar);

        bd = FirebaseFirestore.getInstance();


        etNombre = findViewById(R.id.editTextNombre);
        etPrimerApellido = findViewById(R.id.editTextApellido1);
        etSegundoApellido = findViewById(R.id.editTextApellido2);
        etTelefono= findViewById(R.id.editTextTelefono);
        etMail = findViewById(R.id.editTextMail);
    }

    public void insertarCliente(View view){
        ClienteRepositorio cr = new ClienteRepositorio(bd);
        String nombreCliente = etNombre.getText().toString();
        String primerApellido = etPrimerApellido.getText().toString();
        String segundoApellido = etSegundoApellido.getText().toString();
        String telefono = etTelefono.getText().toString();
        String mail = etMail.getText().toString();

        Cliente c = new Cliente(nombreCliente,primerApellido,segundoApellido,telefono,mail);

        if (!nombreCliente.isEmpty() && !primerApellido.isEmpty() && !segundoApellido.isEmpty() && !telefono.isEmpty() && !mail.isEmpty()) {
            cr.insertar(c)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this,Clientes.class);
                        startActivity(intent);
                    });
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        }
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
    public void volverAtras(View view) {
        finish(); // Cierra la actividad actual y vuelve a la actividad anterior en la pila de actividades.
    }
}