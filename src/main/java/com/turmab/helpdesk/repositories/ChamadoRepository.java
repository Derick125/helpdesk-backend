package com.turmab.helpdesk.repositories;

import com.turmab.helpdesk.domain.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface Repository para a entidade Chamado.
 * Estende JpaRepository para herdar as operações CRUD (Create, Read, Update, Delete)
 * para a entidade Chamado, que é identificada por um ID do tipo Integer.
 */
public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
}