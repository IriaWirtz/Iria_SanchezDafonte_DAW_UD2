package com.dwes.todo.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Datos de una tarea que se muestran en la vista. */
public class TareaDto {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final Long id;
    private final String titulo;
    private final String descripcion;
    private final boolean completada;
    private final LocalDateTime fechaCreacion;

    public TareaDto(Long id, String titulo, String descripcion, boolean completada, LocalDateTime fechaCreacion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = completada;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getFechaFormateada() {
        return fechaCreacion.format(FORMATO);
    }
}
