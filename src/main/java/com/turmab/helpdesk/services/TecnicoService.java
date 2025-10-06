package com.turmab.helpdesk.services;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.turmab.helpdesk.domain.Pessoa;
import com.turmab.helpdesk.domain.Tecnico;
import com.turmab.helpdesk.domain.dtos.TecnicoDTO;
import com.turmab.helpdesk.repositories.PessoaRepository;
import com.turmab.helpdesk.repositories.TecnicoRepository;
import com.turmab.helpdesk.resources.exceptions.DataIntegrityViolationException;
import com.turmab.helpdesk.resources.exceptions.ObjectNotFoundException;

/**
 * Serviço para gerenciar as operações de negócio relacionadas a Técnicos.
 */
@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * Busca um técnico pelo seu ID.
     * @param id O ID do técnico.
     * @return A entidade Tecnico encontrada.
     * @throws ObjectNotFoundException se o técnico não for encontrado.
     */
    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    /**
     * Retorna uma lista com todos os técnicos cadastrados.
     * @return Uma lista de entidades Tecnico.
     */
    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    /**
     * Cria um novo técnico no banco de dados, criptografando a senha.
     * @param objDTO O DTO com os dados do novo técnico.
     * @return A entidade Tecnico recém-criada.
     */
    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha())); // Criptografa a senha
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        return repository.save(newObj);
    }

    /**
     * Atualiza os dados de um técnico existente, criptografando a senha se fornecida.
     * @param id O ID do técnico a ser atualizado.
     * @param objDTO O DTO com os novos dados.
     * @return A entidade Tecnico atualizada.
     */
    public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico oldObj = findById(id);
        
        // Se a senha não for a mesma (criptografada), atualiza
        if (!objDTO.getSenha().equals(oldObj.getSenha())) {
            objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        }

        validaPorCpfEEmail(objDTO);
        oldObj = new Tecnico(objDTO);
        return repository.save(oldObj);
    }

    /**
     * Deleta um técnico do banco de dados.
     * @param id O ID do técnico a ser deletado.
     * @throws DataIntegrityViolationException se o técnico tiver chamados associados.
     */
    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
        }
        repository.deleteById(id);
    }

    /**
     * Valida se o CPF ou E-mail de um DTO já existem no banco de dados para outra pessoa.
     * @param objDTO O DTO a ser validado.
     * @throws DataIntegrityViolationException se o CPF ou E-mail já estiverem em uso.
     */
    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
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