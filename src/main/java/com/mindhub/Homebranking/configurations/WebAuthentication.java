package com.mindhub.Homebranking.configurations;

import com.mindhub.Homebranking.models.Client;
import com.mindhub.Homebranking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration//prog. sepa que se ejecuta antes al resto de metodos
public class WebAuthentication  extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    ClientRepository clientRepository;

    @Bean// ademas de que se fije de correrlo primero y le dice que forma parte del contexto del spring durante la ejecucion del programa
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception { //lanzar error, contemplado(espero que pase, no detiene mi programa) y no contemplados(no pase)
        auth.userDetailsService(inputEmail -> { // buscar detalles del usuario
            Client client = clientRepository.findByEmail(inputEmail);
            if (client != null) {
                if (client.getEmail().equals("adminSupremo@Homebanking.com")) {
                    return new User(client.getEmail(), client.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                }
                return new User(client.getEmail(), client.getPassword(),
                        AuthorityUtils.createAuthorityList("CLIENT"));
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputEmail); //sin s instancio un objeto
            }
        });
    }
}

