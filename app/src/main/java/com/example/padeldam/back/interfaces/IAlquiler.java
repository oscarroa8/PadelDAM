package com.example.padeldam.back.interfaces;

import com.example.padeldam.back.entidades.Alquiler;
import com.example.padeldam.back.entidades.Cliente;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface IAlquiler<T> extends Operaciones<T>{
    Task<List<Alquiler>> findAll();

}
