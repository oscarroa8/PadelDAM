package com.example.padeldam.back.interfaces;

import com.example.padeldam.back.entidades.BotePelotas;
import com.example.padeldam.back.entidades.Palas;
import com.example.padeldam.back.entidades.Pista;
import com.example.padeldam.back.entidades.Zapatillas;
import com.google.android.gms.tasks.Task;

import java.util.List;

/** @noinspection ALL*/
public interface IMateriales<T> extends Operaciones<T>{

    Task<List<BotePelotas>> findAllBotes();
    Task<String> insertarBotePelotas(BotePelotas entidad);

    Task<Void> actualizarBote(BotePelotas entidad);
    Task<Void> devolverBote(BotePelotas entidad);


    Task<List<Zapatillas>> findAllZapatillas();
    Task<String> insertarZapatillas(Zapatillas entidad);

    Task<Void> actualizarZapatillas(Zapatillas entidad);
    Task<Void> devolverZapatillas(Zapatillas entidad);


    Task<List<Palas>> findAllPalas();
    Task<String> insertarPalas(Palas entidad);

    Task<Void> actualizarPalas(Palas entidad);
    Task<Void> devolverPalas(Palas entidad);




        Task<Void> borrarMaterial(String id, String documento,String coleccion);




}
