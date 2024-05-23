package com.example.padeldam;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
public class FechaYHora extends AppCompatActivity {
    private Button buttonFecha;
    private GridLayout gridLayoutHoras;
    private String fechaSeleccionada;
    private String nombrePista;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha_yhora);

        // Obtener los datos de la pista del Intent
        Intent intent = getIntent();
         nombrePista = intent.getStringExtra("nombrePista");

        // Configurar la vista con los datos de la pista
        TextView textView = findViewById(R.id.textViewPista);
        textView.setText(nombrePista);

         buttonFecha = findViewById(R.id.buttonFecha);

        gridLayoutHoras = findViewById(R.id.gridLayoutHoras);



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
                fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(FechaYHora.this, "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();

                buttonFecha.setText(fechaSeleccionada);
                ActualizarBotones();

            }

        }, 2024, 5, 17);
        datePickerDialog.show();
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

        return super.onOptionsItemSelected(item);    }


    private void ActualizarBotones() {
        gridLayoutHoras.removeAllViews();
        int horaInicio = 9; // 9 AM
        int horaFin = 22; // 10 PM

        for (int i = horaInicio; i <= horaFin; i++) {
            Button btn = new Button(this);
            String hora = i < 12 ? i + " AM" : (i == 12 ? "12 PM" : (i - 12) + " PM");
            btn.setText(hora);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            params.setMargins(10, 10, 10, 10);

            btn.setLayoutParams(params);

            gridLayoutHoras.addView(btn, params);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Manejar la selección de hora
                    Intent intent = new Intent(FechaYHora.this, Reservar.class);
                    intent.putExtra("fechaSeleccionada", fechaSeleccionada);
                    intent.putExtra("horaSeleccionada", hora);
                    intent.putExtra("nombrePista", nombrePista);
                    startActivity(intent);
                }
            });
        }
    }


}