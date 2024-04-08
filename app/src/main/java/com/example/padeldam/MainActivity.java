package com.example.padeldam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button entrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.entrar = findViewById(R.id.btnInicio);

    }
    public void cambio (View v){
        Intent i = new Intent(MainActivity.this ,  Login.class);
        startActivity(i);
    }
}