package com.turmab.helpdesk.domain.dtos;

import java.io.Serializable;

/**
 * DTO para encapsular as credenciais (email e senha) enviadas no corpo
 * da requisição de login.
 */
public class CredenciaisDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String senha;

    public CredenciaisDTO() {
        super();
    }
    
    // Getters e Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}