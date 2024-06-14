package com.example.padeldam.back.interfaces;

import com.example.padeldam.back.entidades.Cliente;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface ICliente<T> extends Operaciones<T>{
    Task<List<Cliente>> findAll();

    Task<String> obtenerNombreClientePorId(String clienteId);
}
