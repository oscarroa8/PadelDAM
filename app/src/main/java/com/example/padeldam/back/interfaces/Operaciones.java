package com.example.padeldam.back.interfaces;

import com.google.android.gms.tasks.Task;

public interface Operaciones <T>{
    Task<String> insertar(T entidad);
    Task<Void> actualizar(T entidad);
    Task<Void> borrar(T entidad);
    T getById(String id);
}
