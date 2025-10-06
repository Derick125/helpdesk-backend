package com.turmab.helpdesk.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.turmab.helpdesk.domain.Pessoa;

/**
 * Interface Repository para a entidade Pessoa.
 * Estende JpaRepository para herdar operações CRUD padrão.
 * Inclui métodos de consulta personalizados para buscar por CPF e E-mail.
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    /**
     * Busca uma Pessoa pelo seu CPF.
     * O Spring Data JPA cria a implementação deste método automaticamente.
     * @param cpf O CPF a ser buscado.
     * @return Um Optional contendo a Pessoa, se encontrada.
     */
    Optional<Pessoa> findByCpf(String cpf);

    /**
     * Busca uma Pessoa pelo seu E-mail.
     * O Spring Data JPA cria a implementação deste método automaticamente.
     * @param email O E-mail a ser buscado.
     * @return Um Optional contendo a Pessoa, se encontrada.
     */
    Optional<Pessoa> findByEmail(String email);
}