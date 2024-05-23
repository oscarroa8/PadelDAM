package com.example.padeldam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.padeldam.adaptadores.AdapterClientes;
import com.example.padeldam.back.dao.ClienteRepositorio;
import com.example.padeldam.back.entidades.Cliente;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Clientes extends AppCompatActivity {

    private ListView listaClientes;
    AdapterClientes adaptador;
    ListAdapter adapter;

    ClienteRepositorio cr;

    private ActivityResultLauncher<Intent> editarClienteLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clientes);
        listaClientes = findViewById(R.id.listViewClientes);

        FirebaseFirestore bd = FirebaseFirestore.getInstance();
         cr = new ClienteRepositorio(bd);
        adaptador = new AdapterClientes(Clientes.this,R.layout.card_cliente, new ArrayList<>());
        listaClientes.setAdapter(adaptador);

        cargarClientes();

        adapter= listaClientes.getAdapter();

        editarClienteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Recargar la lista de clientes
                        cargarClientes();
                    }
                }
        );

        listaClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtén el texto del ítem clicado
                Cliente clienteSeleccionado = (Cliente) parent.getItemAtPosition(position);

                Intent intent = new Intent(Clientes.this, EditarCliente.class);
                intent.putExtra("clienteId", clienteSeleccionado.getIdCliente());
                intent.putExtra("clienteNombre", clienteSeleccionado.getNombre());
                intent.putExtra("clienteEmail", clienteSeleccionado.getMail());
                intent.putExtra("clientePrimerApellido", clienteSeleccionado.getApellido1());
                intent.putExtra("clienteSegundoApellido", clienteSeleccionado.getApellido2());
                intent.putExtra("clienteTelefono", clienteSeleccionado.getTelefono());

                editarClienteLauncher.launch(intent);

            }
        });


    }

    private void cargarClientes() {
        cr.findAll().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                adaptador.setClientes(task.getResult());
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

        return super.onOptionsItemSelected(item);
    }

    public void crearCliente (View v){
        Intent i = new Intent(Clientes.this, nuevoCliente.class);
        startActivity(i);
    }

}