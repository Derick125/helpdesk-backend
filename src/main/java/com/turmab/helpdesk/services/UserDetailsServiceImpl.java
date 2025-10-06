package com.turmab.helpdesk.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.turmab.helpdesk.domain.Pessoa;
import com.turmab.helpdesk.repositories.PessoaRepository;
import com.turmab.helpdesk.security.UserSS;

/**
 * Implementação do serviço UserDetailsService do Spring Security.
 * Responsável por carregar os dados de um usuário pelo e-mail para o processo de autenticação.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PessoaRepository repository;

    /**
     * Busca um usuário pelo e-mail (username) e o converte para o formato UserDetails
     * que o Spring Security entende.
     * @param email O e-mail do usuário a ser autenticado.
     * @return Um objeto UserDetails com os dados do usuário.
     * @throws UsernameNotFoundException se o usuário não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Pessoa> user = repository.findByEmail(email);
        if (user.isPresent()) {
            return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getSenha(), user.get().getPerfis());
        }
        throw new UsernameNotFoundException("Usuário não encontrado: " + email);
    }
}