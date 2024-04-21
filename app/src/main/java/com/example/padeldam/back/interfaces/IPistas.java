package com.example.padeldam.back.interfaces;

import com.example.padeldam.back.entidades.Cliente;
import com.example.padeldam.back.entidades.Pista;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface IPistas<T> extends Operaciones<T>{
    Task<List<Pista>> findAll();
}