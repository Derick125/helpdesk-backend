package com.turmab.helpdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal e ponto de entrada para a aplicação Spring Boot Help Desk.
 * A anotação @SpringBootApplication habilita a autoconfiguração do Spring,
 * a varredura de componentes e a configuração da aplicação.
 */
@SpringBootApplication
public class HelpdeskturmabApplication {

	/**
	 * Método principal que é executado para iniciar a aplicação.
	 * @param args Argumentos de linha de comando (não utilizados neste projeto).
	 */
	public static void main(String[] args) {
		SpringApplication.run(HelpdeskturmabApplication.class, args);
	}

}