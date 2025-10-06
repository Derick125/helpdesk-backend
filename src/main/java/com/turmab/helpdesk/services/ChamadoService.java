package com.turmab.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.turmab.helpdesk.domain.Chamado;
import com.turmab.helpdesk.domain.Cliente;
import com.turmab.helpdesk.domain.Tecnico;
import com.turmab.helpdesk.domain.dtos.ChamadoDTO;
import com.turmab.helpdesk.domain.enums.Prioridade;
import com.turmab.helpdesk.domain.enums.Status;
import com.turmab.helpdesk.repositories.ChamadoRepository;
import com.turmab.helpdesk.resources.exceptions.ObjectNotFoundException;

/**
 * Serviço para gerenciar as operações de negócio relacionadas a Chamados.
 */
@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;

    /**
     * Busca um chamado pelo seu ID.
     * @param id O ID do chamado.
     * @return A entidade Chamado encontrada.
     * @throws ObjectNotFoundException se o chamado não for encontrado.
     */
    public Chamado findById(Integer id) {
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    /**
     * Retorna uma lista com todos os chamados cadastrados.
     * @return Uma lista de entidades Chamado.
     */
    public List<Chamado> findAll() {
        return repository.findAll();
    }

    /**
     * Cria um novo chamado no banco de dados.
     * @param objDTO O DTO com os dados do novo chamado.
     * @return A entidade Chamado recém-criada.
     */
    public Chamado create(@Valid ChamadoDTO objDTO) {
        return repository.save(fromDTO(objDTO));
    }
    
    /**
     * Atualiza os dados de um chamado existente.
     * @param id O ID do chamado a ser atualizado.
     * @param objDTO O DTO com os novos dados.
     * @return A entidade Chamado atualizada.
     */
    public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
        objDTO.setId(id);
        findById(id); // Valida se o chamado existe, senão lança exceção
        Chamado newObj = fromDTO(objDTO);
        return repository.save(newObj);
    }

    /**
     * Converte um ChamadoDTO em uma entidade Chamado, associando o técnico e o cliente.
     * @param objDTO O DTO a ser convertido.
     * @return A entidade Chamado.
     */
    private Chamado fromDTO(ChamadoDTO objDTO) {
        Tecnico tecnico = tecnicoService.findById(objDTO.getTecnico());
        Cliente cliente = clienteService.findById(objDTO.getCliente());
        
        Chamado chamado = new Chamado();
        if(objDTO.getId() != null) {
            chamado.setId(objDTO.getId());
        }
        
        // Se o status for "ENCERRADO" (código 2), define a data de fechamento
        if(objDTO.getStatus().equals(2)) {
            chamado.setDataFechamento(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(objDTO.getPrioridade()));
        chamado.setStatus(Status.toEnum(objDTO.getStatus()));
        chamado.setTitulo(objDTO.getTitulo());
        chamado.setObservacoes(objDTO.getObservacoes());
        return chamado;
    }
}