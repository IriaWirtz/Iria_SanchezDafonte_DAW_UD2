package com.dwes.todo.exception;

public class TareaDuplicadaException extends RuntimeException {

    public TareaDuplicadaException(String titulo) {
        super("Ya hay una tarea pendiente con el título «" + titulo + "»");
    }
}
