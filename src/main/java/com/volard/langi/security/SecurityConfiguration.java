package com.volard.langi.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity // what?
public class SecurityConfiguration   {

    private final String [] publicRoutes = {"/", "/login", "/signup", "/users/*", "/test"};

    @Bean
    @Lazy
    public PasswordEncoder bPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    DelegatingServerLogoutHandler logoutHandler = new DelegatingServerLogoutHandler(
            new WebSessionServerLogoutHandler(), new SecurityContextServerLogoutHandler()
    );

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable) // todo unless I haven't understand this
            .authorizeExchange( exchanges -> exchanges
                .pathMatchers(publicRoutes).permitAll()
                .anyExchange().authenticated()
            )
            .formLogin(Customizer.withDefaults())

            .logout((logoutSpec -> logoutSpec.logoutHandler(logoutHandler))) // invalidate the session on logout
            .httpBasic(Customizer.withDefaults())
        ;

        return http.build();
    }

}
