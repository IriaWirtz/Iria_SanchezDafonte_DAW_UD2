package com.dwes.todo.repository;

import com.dwes.todo.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    /** Pendientes primero y, dentro de cada grupo, las más recientes arriba. */
    List<Tarea> findAllByOrderByCompletadaAscFechaCreacionDesc();

    long countByCompletadaFalse();

    boolean existsByTituloIgnoreCaseAndCompletadaFalse(String titulo);
}
