package com.example.padeldam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.padeldam.adaptadores.AdapterClientes;
import com.example.padeldam.back.dao.ClienteRepositorio;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Clientes extends AppCompatActivity {

    private ListView listaClientes;
    AdapterClientes adaptador;
    ListAdapter adapter;

    private DrawerLayout drawerLayout;
    private ImageView menu;
    LinearLayout home,clientes,logout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clientes);
        listaClientes = findViewById(R.id.listViewClientes);

        FirebaseFirestore bd = FirebaseFirestore.getInstance();
        ClienteRepositorio cr = new ClienteRepositorio(bd);
        adaptador = new AdapterClientes(Clientes.this,R.layout.card_cliente, new ArrayList<>());
        listaClientes.setAdapter(adaptador);
        cr.findAll().addOnCompleteListener(task -> {
            adaptador.setClientes(task.getResult());
        });

        adapter= listaClientes.getAdapter();

        drawerLayout = findViewById(R.id.drawer_layout);
        menu =findViewById(R.id.menu);
        home = findViewById(R.id.home);
        clientes = findViewById(R.id.clientes);
        logout = findViewById(R.id.logout);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarActivity(Clientes.this, Menu.class);
            }
        });
        clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Clientes.this, "logout", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void crearCliente (View v){
        Intent i = new Intent(Clientes.this, nuevoCliente.class);
        startActivity(i);
    }



    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.itemC){
            Intent intent = new Intent(this,Clientes.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);    }

    public static void openDrawer(DrawerLayout dl){
        dl.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout dl){
        if(dl.isDrawerOpen(GravityCompat.START)){
            dl.closeDrawer(GravityCompat.START);
        }
    }
    public static void cambiarActivity (Activity a, Class b){
        Intent i = new Intent(a,b);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        a.startActivity(i);
        a.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }


}