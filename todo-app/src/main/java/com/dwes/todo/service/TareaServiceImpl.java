package com.dwes.todo.service;

import com.dwes.todo.dto.TareaCrearDto;
import com.dwes.todo.dto.TareaDto;
import com.dwes.todo.entity.Tarea;
import com.dwes.todo.exception.TareaDuplicadaException;
import com.dwes.todo.exception.TareaNoEncontradaException;
import com.dwes.todo.mapper.TareaMapper;
import com.dwes.todo.repository.TareaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TareaServiceImpl implements TareaService {

    private final TareaRepository repository;
    private final TareaMapper mapper;

    public TareaServiceImpl(TareaRepository repository, TareaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaDto> listar() {
        return mapper.toDtoList(repository.findAllByOrderByCompletadaAscFechaCreacionDesc());
    }

    @Override
    @Transactional(readOnly = true)
    public long contarPendientes() {
        return repository.countByCompletadaFalse();
    }

    @Override
    public TareaDto crear(TareaCrearDto dto) {
        String titulo = dto.getTitulo().strip();
        if (repository.existsByTituloIgnoreCaseAndCompletadaFalse(titulo)) {
            throw new TareaDuplicadaException(titulo);
        }
        Tarea guardada = repository.save(mapper.toEntity(dto));
        return mapper.toDto(guardada);
    }

    @Override
    public void alternarCompletada(Long id) {
        Tarea tarea = buscar(id);
        tarea.setCompletada(!tarea.isCompletada());
    }

    @Override
    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    private Tarea buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new TareaNoEncontradaException(id));
    }
}
