package com.example.padeldam;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class menuPrincipal extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Snackbar.make(findViewById(android.R.id.content),"Bienvenido "+currentUser.getEmail(),Snackbar.LENGTH_SHORT).show();

        ImageView ivPistas = findViewById(R.id.ivPistas);
        ImageView ivAlquiler = findViewById(R.id.ivAlquiler);

//

        ivPistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.aumento_imagen);
                ivPistas.startAnimation(anim);
                Intent i = new Intent(menuPrincipal.this, Pistas.class);
                startActivity(i);
            }
        });

        ivAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.aumento_imagen);
                ivAlquiler.startAnimation(anim);
                Intent i = new Intent(menuPrincipal.this, Alquiler.class);
                startActivity(i);
            }
        });
//
//

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

        return super.onOptionsItemSelected(item);    }

    // Método para realizar alguna acción que afecte al menú


    public void botonPrueba (View view){
        Intent i = new Intent(menuPrincipal.this, Clientes.class);
        startActivity(i);
    }





}