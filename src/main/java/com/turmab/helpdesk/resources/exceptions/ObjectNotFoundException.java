package com.turmab.helpdesk.resources.exceptions;

/**
 * Exceção personalizada para quando um objeto não é encontrado no banco de dados.
 * Lançada pela camada de serviço quando uma busca por ID não retorna resultados.
 */
public class ObjectNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }
}