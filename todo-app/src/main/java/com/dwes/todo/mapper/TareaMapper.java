package com.dwes.todo.mapper;

import com.dwes.todo.dto.TareaCrearDto;
import com.dwes.todo.dto.TareaDto;
import com.dwes.todo.entity.Tarea;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TareaMapper {

    public TareaDto toDto(Tarea tarea) {
        return new TareaDto(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.isCompletada(),
                tarea.getFechaCreacion());
    }

    public List<TareaDto> toDtoList(List<Tarea> tareas) {
        return tareas.stream().map(this::toDto).toList();
    }

    public Tarea toEntity(TareaCrearDto dto) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(dto.getTitulo().strip());
        String descripcion = dto.getDescripcion();
        tarea.setDescripcion(descripcion == null || descripcion.isBlank() ? null : descripcion.strip());
        return tarea;
    }
}
