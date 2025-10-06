package com.turmab.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.turmab.helpdesk.domain.Chamado;
import com.turmab.helpdesk.domain.dtos.ChamadoDTO;
import com.turmab.helpdesk.services.ChamadoService;

/**
 * Controller REST para gerenciar as operações CRUD da entidade Chamado.
 * Expõe os endpoints relacionados a /chamados.
 */
@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {

    @Autowired
    private ChamadoService service;

    /**
     * Endpoint para buscar um chamado pelo ID.
     * @param id O ID do chamado a ser buscado.
     * @return ResponseEntity contendo o ChamadoDTO do chamado encontrado.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
        Chamado obj = service.findById(id);
        return ResponseEntity.ok().body(new ChamadoDTO(obj));
    }

    /**
     * Endpoint para listar todos os chamados cadastrados.
     * @return ResponseEntity contendo uma lista de ChamadoDTO.
     */
    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll() {
        List<Chamado> list = service.findAll();
        List<ChamadoDTO> listDTO = list.stream().map(ChamadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    /**
     * Endpoint para criar um novo chamado.
     * @param objDTO O DTO com os dados do novo chamado.
     * @return ResponseEntity com status 201 Created e a URL do novo recurso no cabeçalho Location.
     */
    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO objDTO) {
        Chamado obj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    /**
     * Endpoint para atualizar os dados de um chamado existente.
     * @param id O ID do chamado a ser atualizado.
     * @param objDTO O DTO com os novos dados.
     * @return ResponseEntity contendo o ChamadoDTO com os dados atualizados.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO objDTO) {
        Chamado newObj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ChamadoDTO(newObj));
    }
}