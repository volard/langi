package com.volard.langi.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity // what?
@AllArgsConstructor
public class SecurityConfiguration {

    private final String[] publicRoutes = {"/", "/login", "/signup", "/users/*", "/test", "/prikol"};

    @Bean
    public PasswordEncoder bPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    DelegatingServerLogoutHandler logoutHandler = new DelegatingServerLogoutHandler(
//            new WebSessionServerLogoutHandler(), new SecurityContextServerLogoutHandler()
//    );

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // todo unless I haven't configured this
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(publicRoutes).permitAll()
                        .anyExchange().authenticated()
                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(withDefaults())
//                )
                .formLogin(withDefaults())
//                .logout((logoutSpec -> logoutSpec.logoutHandler(logoutHandler))) // invalidate the session on logout
                .httpBasic(withDefaults())
        ;

        return http.build();
    }

    // todo authenticationFilter

//    AuthenticationWebFilter bearerAuthenticationFilter(AuthenticationManager authenticationManager){
//        AuthenticationWebFilter bearerAuthenticationFilter
//                = new AuthenticationWebFilter(authenticationManager);
//        bearerAuthenticationFilter.setServerAuthenticationConverter(
//                new ServerHttpBearerAuthenticationConverter(
//                                new JwtVerifyHandler(jwtSecret)
//                        )
//        );
//        bearerAuthenticationFilter
//                .setRequiresAuthenticationMatcher(
//                        ServerWebExchangeMatchers.pathMatchers("/**")
//                );
//
//
//        return bearerAuthenticationFilter;
//    }

}
