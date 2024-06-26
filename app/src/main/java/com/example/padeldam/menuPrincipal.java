package com.example.padeldam;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/** @noinspection ALL*/
public class menuPrincipal extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setTitle("menu");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        ImageView ivPistas = findViewById(R.id.ivPistas);
        ImageView ivAlquiler = findViewById(R.id.ivAlquiler);


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
                Intent i = new Intent(menuPrincipal.this, Alquilar.class);
                startActivity(i);
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
            Intent intent = new Intent(this,Clientes.class);
            startActivity(intent);
        }
        if(id == R.id.itemHome){
            Intent intent = new Intent(this,menuPrincipal.class);
            startActivity(intent);
        }
        if(id == R.id.itemLogout){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent intent = new Intent(this,Login.class);
            Toast.makeText(getApplicationContext(), "Sesion finalizada", Toast.LENGTH_SHORT).show();

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);    }


}