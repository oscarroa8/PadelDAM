package com.example.padeldam;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class EditarCliente extends AppCompatActivity {

    private EditText etNombre;
    private EditText etPrimerApellido;
    private EditText etSegundoApellido;
    private EditText etMail;
    private EditText etTelefono;

    private String clienteId;
    private ClienteRepositorio clienteRepositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);

        etNombre = findViewById(R.id.editTextNombre);
        etPrimerApellido = findViewById(R.id.editTextApellido1);
        etSegundoApellido = findViewById(R.id.editTextApellido2);
        etTelefono= findViewById(R.id.editTextTelefono);
        etMail = findViewById(R.id.editTextMail);
        // Inicializa otros campos de formulario

        // Obtener los datos del Intent
        Intent intent = getIntent();
        clienteId = intent.getStringExtra("clienteId");
        String clienteNombre = intent.getStringExtra("clienteNombre");
        String clienteEmail = intent.getStringExtra("clienteEmail");
        String primerApelli = intent.getStringExtra("clientePrimerApellido");
        String segundoApelli = intent.getStringExtra("clienteSegundoApellido");
        String telefono = intent.getStringExtra("clienteTelefono");

        // Llena los campos del formulario
        etNombre.setText(clienteNombre);
        etPrimerApellido.setText(primerApelli);
        etSegundoApellido.setText(segundoApelli);
        etTelefono.setText(telefono);
        etMail.setText(clienteEmail);

        // Inicializa ClienteRepositorio
        FirebaseFirestore bd = FirebaseFirestore.getInstance();
        clienteRepositorio = new ClienteRepositorio(bd);
    }

    // Método para guardar los cambios del cliente
    public void actualizarCliente(View view) {
        String nuevoNombre = etNombre.getText().toString();
        String nuevoEmail = etMail.getText().toString();
        String nuevoApellido1 = etPrimerApellido.getText().toString();
        String nuevoApellido2 = etSegundoApellido.getText().toString();
        String nuevoTelefono = etTelefono.getText().toString();

        Cliente clienteActualizado = new Cliente(clienteId, nuevoNombre, nuevoApellido1,nuevoApellido2,nuevoTelefono,nuevoEmail);


        clienteRepositorio.actualizar(clienteActualizado)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditarCliente.this, "Cliente actualizado", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditarCliente.this, "Error al actualizar cliente", Toast.LENGTH_SHORT).show();
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
