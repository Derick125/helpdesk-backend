package com.turmab.helpdesk.repositories;

import com.turmab.helpdesk.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface Repository para a entidade Tecnico.
 * Estende JpaRepository para herdar as operações CRUD (Create, Read, Update, Delete)
 * para a entidade Tecnico, que é identificada por um ID do tipo Integer.
 */
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
}