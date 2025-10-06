package com.turmab.helpdesk.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.turmab.helpdesk.domain.enums.Perfil;

/**
 * Implementação personalizada de {@link UserDetails} para o Spring Security.
 * Esta classe adapta a entidade Pessoa do sistema para o formato que o Spring Security entende,
 * contendo as informações necessárias para autenticação e autorização.
 */
public class UserSS implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
        super();
        this.id = id;
        this.email = email;
        this.senha = senha;
        // Converte os perfis do sistema em autoridades compreendidas pelo Spring Security
        this.authorities = perfis.stream()
                .map(p -> new SimpleGrantedAuthority(p.getDescricao()))
                .collect(Collectors.toSet());
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return senha;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}