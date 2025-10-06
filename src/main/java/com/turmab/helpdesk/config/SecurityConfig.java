package com.turmab.helpdesk.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.turmab.helpdesk.security.JWTAuthenticationFilter;
import com.turmab.helpdesk.security.JWTAuthorizationFilter;
import com.turmab.helpdesk.security.JWTUtil;

/**
 * Classe principal de configuração de segurança do Spring Security.
 * Habilita a segurança da aplicação e a segurança a nível de método.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };

    /**
     * Configura as regras de segurança HTTP, como CORS, CSRF, gerenciamento de sessão
     * e permissões de acesso aos endpoints.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Libera o acesso ao console do H2 no perfil de teste
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        // Habilita o CORS com a configuração definida no Bean e desabilita o CSRF
        http.cors().and().csrf().disable();
        
        // Adiciona os filtros de autenticação e autorização JWT
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        
        // Define que a aplicação não criará sessões de usuário (stateless)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        // Define as permissões de acesso para os endpoints
        http.authorizeRequests()
            .antMatchers(PUBLIC_MATCHERS).permitAll() // Permite acesso público aos matchers definidos
            .anyRequest().authenticated(); // Exige autenticação para todas as outras requisições
    }

    /**
     * Configura o mecanismo de autenticação, informando ao Spring Security
     * qual UserDetailsService e qual PasswordEncoder utilizar.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    
    /**
     * Disponibiliza um Bean de BCryptPasswordEncoder para ser usado em toda a aplicação
     * para criptografar e verificar senhas.
     * @return A instância do BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Disponibiliza um Bean com as configurações de CORS (Cross-Origin Resource Sharing),
     * permitindo que o frontend (rodando em outra porta/domínio) acesse a API.
     * @return A fonte de configuração do CORS.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}