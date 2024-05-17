package com.example.padeldam;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class FechaYHora extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha_yhora);

        // Obtener los datos de la pista del Intent
        Intent intent = getIntent();
        String nombrePista = intent.getStringExtra("nombrePista");

        // Configurar la vista con los datos de la pista
        TextView textView = findViewById(R.id.textViewPista);
        textView.setText(nombrePista);

        Button buttonFecha = findViewById(R.id.buttonFecha);


        buttonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

//
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Manejar la fecha seleccionada
                String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(FechaYHora.this, "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();
            }
        }, 2024, 5, 17);
        datePickerDialog.show();
    }






}