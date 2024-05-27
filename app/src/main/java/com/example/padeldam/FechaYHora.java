package com.example.padeldam;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import androidx.core.content.ContextCompat;

import com.example.padeldam.back.dao.ReservasRepositorio;
import com.example.padeldam.back.entidades.Reserva;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        fechaSeleccionada = intent.getStringExtra("fechaSeleccionada");

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
        //Con esto saco el dia de hoy para que se actualice el calendario
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Manejar la fecha seleccionada
                fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(FechaYHora.this, "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();

                buttonFecha.setText(fechaSeleccionada);
                ActualizarBotones();

            }

        }, year, month, dayOfMonth);
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
        final Context context = this; // Referencia a la actividad


        // Obtener todas las reservas
        ReservasRepositorio rp = new ReservasRepositorio(FirebaseFirestore.getInstance());
        rp.findAll().addOnCompleteListener(new OnCompleteListener<List<Reserva>>() {

            @Override
            public void onComplete(@NonNull Task<List<Reserva>> task) {
                if (task.isSuccessful()) {
                    List<Reserva> reservas = task.getResult();

                    // Filtrar reservas para la fecha y pista seleccionadas
                    List<Reserva> reservasFiltradas = new ArrayList<>();
                    for (Reserva reserva : reservas) {
                        if (reserva.getFecha().equals(fechaSeleccionada) && reserva.getPista().equals(nombrePista)) {
                            reservasFiltradas.add(reserva);
                        }
                    }

                    // Crear botones para cada hora y cambiar el color si está reservada
                    for (int i = horaInicio; i <= horaFin; i++) {
                        Button btn = new Button(FechaYHora.this);
                        String hora = i < 12 ? i + " AM" : (i == 12 ? "12 PM" : (i - 12) + " PM");
                        btn.setText(hora);

                        // Verificar si la hora está reservada
                        Reserva reservaActual = null;
                        for (Reserva reserva : reservasFiltradas) {
                            if (reserva.getHora().equals(hora)) {
                                reservaActual = reserva;
                                break;
                            }
                        }

                        if (reservaActual != null) {
                            btn.setBackgroundColor(ContextCompat.getColor(context, R.color.reservado));
                            btn.setTag(reservaActual); // Guardar la reserva en el botón
                        } else {
                            btn.setBackgroundColor(ContextCompat.getColor(context, R.color.noReservado));
                        }

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
                                int color = ((ColorDrawable) btn.getBackground()).getColor();
                                if (color ==  ContextCompat.getColor(context, R.color.reservado)) {
                                    // Mostrar diálogo de confirmación para cancelar la reserva
                                    mostrarDialogoCancelarReserva((Reserva) btn.getTag());
                                } else {
                                    // Manejar la selección de hora para nueva reserva
                                    Intent intent = new Intent(FechaYHora.this, Reservar.class);
                                    intent.putExtra("fechaSeleccionada", fechaSeleccionada);
                                    intent.putExtra("horaSeleccionada", hora);
                                    intent.putExtra("nombrePista", nombrePista);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                } else {
                    // Manejar error al obtener reservas
                   // Log.e(TAG, "Error al obtener reservas", task.getException());
                    Toast.makeText(FechaYHora.this, "Error al cargar reservas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mostrarDialogoCancelarReserva(Reserva reserva) {
        new AlertDialog.Builder(this)
                .setTitle("Cancelar Reserva")
                .setMessage("¿Estás seguro de que deseas cancelar la reserva de la hora " + reserva.getHora() + " para la pista " + reserva.getPista() + " para el cliente "+reserva.getCliente()+"?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancelar la reserva
                        cancelarReserva(reserva);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void cancelarReserva(Reserva reserva) {
        ReservasRepositorio rp = new ReservasRepositorio(FirebaseFirestore.getInstance());
        rp.borrar(reserva).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(FechaYHora.this, "Reserva cancelada", Toast.LENGTH_SHORT).show();
                    ActualizarBotones(); // Actualizar botones después de cancelar la reserva
                } else {
                    Toast.makeText(FechaYHora.this, "Error al cancelar la reserva", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }







}