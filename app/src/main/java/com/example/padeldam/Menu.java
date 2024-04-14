package com.example.padeldam;

import android.content.Intent;
import android.os.Bundle;
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

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Snackbar.make(findViewById(android.R.id.content),"Bienvenido "+currentUser.getEmail(),Snackbar.LENGTH_SHORT).show();

        ImageView ivPistas = findViewById(R.id.ivPistas);
        ImageView ivAlquiler = findViewById(R.id.ivAlquiler);
        ivPistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.aumento_imagen);
                ivPistas.startAnimation(anim);
                Intent i = new Intent(Menu.this, Pistas.class);
                startActivity(i);
            }
        });

        ivAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.aumento_imagen);
                ivAlquiler.startAnimation(anim);
                Intent i = new Intent(Menu.this, Alquiler.class);
                startActivity(i);
            }
        });



    }
    public void botonPrueba (View view){
        Intent i = new Intent(Menu.this, Clientes.class);
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
}