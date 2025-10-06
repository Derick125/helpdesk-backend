package com.turmab.helpdesk.resources.exceptions;

/**
 * Exceção personalizada para violações de integridade de dados.
 * Lançada quando uma operação viola uma regra de negócio, como a tentativa
 * de cadastrar um CPF ou e-mail que já existe.
 */
public class DataIntegrityViolationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataIntegrityViolationException(String message) {
        super(message);
    }
}