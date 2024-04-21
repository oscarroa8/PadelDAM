package com.example.padeldam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
}