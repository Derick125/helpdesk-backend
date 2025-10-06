package com.turmab.helpdesk.services;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.turmab.helpdesk.domain.Pessoa;
import com.turmab.helpdesk.domain.Cliente;
import com.turmab.helpdesk.domain.dtos.ClienteDTO;
import com.turmab.helpdesk.repositories.PessoaRepository;
import com.turmab.helpdesk.repositories.ClienteRepository;
import com.turmab.helpdesk.resources.exceptions.DataIntegrityViolationException;
import com.turmab.helpdesk.resources.exceptions.ObjectNotFoundException;

/**
 * Serviço para gerenciar as operações de negócio relacionadas a Clientes.
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * Busca um cliente pelo seu ID.
     * @param id O ID do cliente.
     * @return A entidade Cliente encontrada.
     * @throws ObjectNotFoundException se o cliente não for encontrado.
     */
    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    /**
     * Retorna uma lista com todos os clientes cadastrados.
     * @return Uma lista de entidades Cliente.
     */
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    /**
     * Cria um novo cliente no banco de dados, criptografando a senha.
     * @param objDTO O DTO com os dados do novo cliente.
     * @return A entidade Cliente recém-criada.
     */
    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return repository.save(newObj);
    }

    /**
     * Atualiza os dados de um cliente existente, criptografando a senha se fornecida.
     * @param id O ID do cliente a ser atualizado.
     * @param objDTO O DTO com os novos dados.
     * @return A entidade Cliente atualizada.
     */
    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        
        if (!objDTO.getSenha().equals(oldObj.getSenha())) {
            objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        }
        
        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return repository.save(oldObj);
    }

    /**
     * Deleta um cliente do banco de dados.
     * @param id O ID do cliente a ser deletado.
     * @throws DataIntegrityViolationException se o cliente tiver chamados associados.
     */
    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
        }
        repository.deleteById(id);
    }

    /**
     * Valida se o CPF ou E-mail de um DTO já existem no banco de dados para outra pessoa.
     * @param objDTO O DTO a ser validado.
     * @throws DataIntegrityViolationException se o CPF ou E-mail já estiverem em uso.
     */
    private void validaPorCpfEEmail(ClienteDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }
}