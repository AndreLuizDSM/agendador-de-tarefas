package com.javanauta.agendadortarefas.infrastructure.exceptions;

public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String message) {
        super(message);
    }
    public ResourceNotFound(String message, Throwable throwable){
        super(message, throwable);
    }
}
