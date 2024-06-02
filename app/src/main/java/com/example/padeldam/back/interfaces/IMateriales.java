package com.example.padeldam.back.interfaces;

import com.example.padeldam.back.entidades.BotePelotas;
import com.example.padeldam.back.entidades.Pista;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface IMateriales<T> extends Operaciones<T>{

    Task<List<BotePelotas>> findAllBotes();
    Task<String> insertarBotePelotas(BotePelotas entidad);



}
