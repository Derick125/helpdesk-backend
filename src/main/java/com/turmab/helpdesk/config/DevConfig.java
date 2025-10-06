package com.turmab.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.turmab.helpdesk.services.DBService;

/**
 * Classe de configuração específica para o perfil de desenvolvimento ("dev").
 * Esta configuração é ativada quando a propriedade 'spring.profiles.active' é definida como 'dev'.
 */
@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    /**
     * Injeta o valor da propriedade 'spring.jpa.hibernate.ddl-auto' do arquivo application-dev.properties.
     * Este valor determina a estratégia de geração do schema do banco de dados (ex: "create", "update", "none").
     */
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    /**
     * Cria um Bean que executa o método de instanciação do banco de dados.
     * A lógica condicional garante que o banco de dados só seja populado se a estratégia
     * de DDL for "create", evitando a reinserção de dados a cada reinicialização da aplicação
     * quando a estratégia for "update" ou "none".
     *
     * @return false para indicar que o Bean foi processado, sem necessidade de um valor de retorno específico.
     */
    @Bean
    public boolean instanciaDB() {
        if ("create".equals(strategy)) {
            this.dbService.instanciaDB();
        }
        return false;
    }
}