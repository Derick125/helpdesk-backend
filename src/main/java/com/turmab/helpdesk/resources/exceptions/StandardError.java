package com.turmab.helpdesk.resources.exceptions;

import java.io.Serializable;

/**
 * Classe DTO que padroniza o corpo da resposta para erros HTTP na aplicação.
 * Contém informações como timestamp, status, mensagem de erro e o caminho da requisição.
 */
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError() {
        super();
    }

    /**
     * Construtor completo para criar uma instância de StandardError.
     *
     * @param timestamp O momento em que o erro ocorreu (em milissegundos).
     * @param status O código de status HTTP (ex: 404, 400).
     * @param error A descrição do status HTTP (ex: "Not Found", "Bad Request").
     * @param message A mensagem detalhada da exceção.
     * @param path O URI da requisição que causou o erro.
     */
    public StandardError(Long timestamp, Integer status, String error, String message, String path) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Getters e Setters
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}