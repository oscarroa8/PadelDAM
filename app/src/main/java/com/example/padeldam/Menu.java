package com.example.padeldam;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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