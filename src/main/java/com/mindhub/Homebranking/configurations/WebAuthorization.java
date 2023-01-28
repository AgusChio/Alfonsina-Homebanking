package com.mindhub.Homebranking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity //Habilita la seguridad en el sitio web, usando la librería Spring Security.
@Configuration
public class WebAuthorization {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/clients","/api/generatePdf").permitAll()//indicar permisos a los tipos de usuarios para que tengan ingreso a los diferentes recursos del proyecto
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts", "api/clients/current/cards", "api/transactions","api/transactions/payments").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/loans/admin").hasAuthority("ADMIN")
                .antMatchers("/rest/**", "/h2-console", "/web/manager.html","/web/new-Loan.html" ,"/api/clients").hasAuthority("ADMIN")
                .antMatchers("/web/index.html","/fontawesome-free-6.2.1-web/**", "/web/Assets/**", "/web/JavaScript/**", "/web/login.html", "/web/registration.html").permitAll()
                .antMatchers("/web/**", "/api/clients/current").hasAuthority("CLIENT")
                .antMatchers("/**").permitAll();


        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");


//cross side request forgery, deshabilitamos el token(Es una cadena cifrada que identifica la sesión de un usuario), para poder hacer peticiones.
        http.csrf().disable();

// disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();

// que pasa si un usuario no esta autenticado y quiere entrar a una pagina
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

// if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

// if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

// if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

// si no esta autenticado y quiere acceder a otro recurso en el cual no esta autorizado  los va a mandar al login.
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendRedirect("/web/login.html"));

        return http.build();
    }


    // LIBERA BANDERA = HACE REFERECIAN A LA AUTENTICACION
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}

