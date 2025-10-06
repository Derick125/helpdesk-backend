package com.turmab.helpdesk.repositories;

import com.turmab.helpdesk.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface Repository para a entidade Cliente.
 * Estende JpaRepository para herdar as operações CRUD (Create, Read, Update, Delete)
 * para a entidade Cliente, que é identificada por um ID do tipo Integer.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}