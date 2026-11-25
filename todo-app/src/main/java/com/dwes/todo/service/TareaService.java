package com.dwes.todo.service;

import com.dwes.todo.dto.TareaCrearDto;
import com.dwes.todo.dto.TareaDto;

import java.util.List;

public interface TareaService {

    List<TareaDto> listar();

    long contarPendientes();

    TareaDto crear(TareaCrearDto dto);

    /** Marca la tarea como completada o, si ya lo estaba, de nuevo como pendiente. */
    void alternarCompletada(Long id);

    void eliminar(Long id);
}
