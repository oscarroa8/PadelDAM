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

import com.example.padeldam.back.dao.ClienteRepositorio;
import com.example.padeldam.back.entidades.Cliente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/** @noinspection ALL*/
public class nuevoCliente extends AppCompatActivity {
    Button btnCrear;
    EditText etNombre,etPrimerApellido,etSegundoApellido,etTelefono,etMail;
    FirebaseFirestore bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nuevo_cliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnCrear = findViewById(R.id.btnGuardar);

        bd = FirebaseFirestore.getInstance();


        etNombre = findViewById(R.id.editTextNombre);
        etPrimerApellido = findViewById(R.id.editTextApellido1);
        etSegundoApellido = findViewById(R.id.editTextApellido2);
        etTelefono= findViewById(R.id.editTextTelefono);
        etMail = findViewById(R.id.editTextMail);
    }

    public void insertarCliente(View view) {
        ClienteRepositorio cr = new ClienteRepositorio(bd);
        String nombreCliente = etNombre.getText().toString().trim();
        String primerApellido = etPrimerApellido.getText().toString().trim();
        String segundoApellido = etSegundoApellido.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String mail = etMail.getText().toString().trim();

        // Validar campos vacíos
        if (nombreCliente.isEmpty() || primerApellido.isEmpty() || segundoApellido.isEmpty() || telefono.isEmpty() || mail.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar formato del correo electrónico
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(this, "Correo electrónico no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar formato del teléfono (ejemplo simple, puede ser más complejo)
        if (!telefono.matches("\\d{9}")) {
            Toast.makeText(this, "Número de teléfono no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear una nueva instancia de Cliente
        Cliente c = new Cliente(nombreCliente, primerApellido, segundoApellido, telefono, mail);

        // Insertar en el repositorio
        cr.insertar(c)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Cliente creado correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, Clientes.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Error al insertar datos", Toast.LENGTH_SHORT).show();
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