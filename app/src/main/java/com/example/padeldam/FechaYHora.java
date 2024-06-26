package com.example.padeldam;


import android.app.DatePickerDialog;
import android.content.Context;

import android.content.Intent;

import android.graphics.drawable.ColorDrawable;
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
import androidx.core.content.ContextCompat;

import com.example.padeldam.back.dao.PistaRepositorio;
import com.example.padeldam.back.dao.ReservasRepositorio;
import com.example.padeldam.back.entidades.Pista;
import com.example.padeldam.back.entidades.Reserva;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/** @noinspection ALL*/
public class FechaYHora extends AppCompatActivity {
    private Button buttonFecha;
    private GridLayout gridLayoutHoras;
    private String fechaSeleccionada;
    private String nombrePista;

    private String idPista;

    private FirebaseFirestore db;

    private PistaRepositorio pistasRepositorio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha_yhora);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();


        pistasRepositorio = new PistaRepositorio(db);

        // Obtener los datos de la pista del Intent
        Intent intent = getIntent();
        // nombrePista = intent.getStringExtra("nombrePista");
        fechaSeleccionada = intent.getStringExtra("fechaSeleccionada");
        idPista = intent.getStringExtra("idPista");

        // Configurar la vista con los datos de la pista
        TextView textViewPista = findViewById(R.id.textViewPista);

        pistasRepositorio.obtenerPistaPorId(idPista).addOnCompleteListener(new OnCompleteListener<Pista>() {
            @Override
            public void onComplete(@NonNull Task<Pista> task) {
                if (task.isSuccessful()) {
                    Pista pista = task.getResult();
                    if (pista != null) {
                        textViewPista.setText("Pista: " + pista.getNombre());
                    } else {
                        textViewPista.setText("Pista no encontrada");
                    }
                } else {
                    textViewPista.setText("Error al obtener la pista");
                }
            }
        });


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
        if(id==android.R.id.home){
            Intent intent = new Intent(this,Pistas.class);//Falta crear la clase usuarios
            startActivity(intent);
        }
        if(id == R.id.itemCliente){
            Intent intent = new Intent(this,Clientes.class);//Falta crear la clase usuarios
            startActivity(intent);
        }
        if(id == R.id.itemHome){
            Intent intent = new Intent(this,menuPrincipal.class);//Falta crear la clase usuarios
            startActivity(intent);
        }
        if(id == R.id.itemLogout){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent intent = new Intent(this,Login.class);//Falta crear la clase usuarios
            Toast.makeText(getApplicationContext(), "Sesion finalizada", Toast.LENGTH_SHORT).show();

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
                        if (fechaSeleccionada != null && idPista != null
                                && fechaSeleccionada.equals(reserva.getFecha())
                                && idPista.equals(reserva.getIdPista())) {
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
                                    intent.putExtra("idPista", idPista);
                                    intent.putExtra("fechaSeleccionada", fechaSeleccionada);
                                    intent.putExtra("horaSeleccionada", hora);
                                  //  intent.putExtra("nombrePista", nombrePista);
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
        Intent intent = new Intent(this, DetallesReserva.class);
        intent.putExtra("reserva", reserva);
        startActivity(intent);
    }


}