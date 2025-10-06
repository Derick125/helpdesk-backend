package com.turmab.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.turmab.helpdesk.domain.Cliente;
import com.turmab.helpdesk.domain.dtos.ClienteDTO;
import com.turmab.helpdesk.services.ClienteService;

/**
 * Controller REST para gerenciar as operações CRUD da entidade Cliente.
 * Expõe os endpoints relacionados a /clientes.
 */
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    /**
     * Endpoint para buscar um cliente pelo ID.
     * @param id O ID do cliente a ser buscado.
     * @return ResponseEntity contendo o ClienteDTO do cliente encontrado.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }

    /**
     * Endpoint para listar todos os clientes cadastrados.
     * @return ResponseEntity contendo uma lista de ClienteDTO.
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDTO = list.stream().map(ClienteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    /**
     * Endpoint para criar um novo cliente.
     * @param objDTO O DTO com os dados do novo cliente.
     * @return ResponseEntity com status 201 Created e a URL do novo recurso no cabeçalho Location.
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO) {
        Cliente newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Endpoint para atualizar os dados de um cliente existente.
     * @param id O ID do cliente a ser atualizado.
     * @param objDTO O DTO com os novos dados.
     * @return ResponseEntity contendo o ClienteDTO com os dados atualizados.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO) {
        Cliente obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }

    /**
     * Endpoint para deletar um cliente.
     * @param id O ID do cliente a ser deletado.
     * @return ResponseEntity com status 204 No Content.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}