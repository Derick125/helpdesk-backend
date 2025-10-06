package com.turmab.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.turmab.helpdesk.services.DBService;

/**
 * Classe de configuração específica para o perfil de teste ("test").
 * Esta configuração é ativada para popular o banco de dados em memória (H2)
 * quando a aplicação é iniciada com o perfil de teste.
 */
@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    /**
     * Cria um Bean que executa o método de instanciação do banco de dados.
     * Este método é chamado automaticamente pelo Spring na inicialização da aplicação
     * quando o perfil "test" está ativo.
     */
    @Bean
    public void instanciaDB() {
        this.dbService.instanciaDB();
    }
}