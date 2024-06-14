package com.example.padeldam.back.interfaces;

import com.example.padeldam.back.entidades.Pista;
import com.example.padeldam.back.entidades.Reserva;
import com.google.android.gms.tasks.Task;

import java.util.List;

/** @noinspection ALL*/
public interface IReserva<T> extends Operaciones<T>{

    Task<List<Reserva>> findAll();

}
