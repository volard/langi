package com.volard.langi.security.utils;

import com.volard.langi.security.AuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Strategy used for persisting a {@link SecurityContext} between requests.
 * @see ServerSecurityContextRepository
 */
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final AuthenticationManager authenticationManager;
    private final String authScheme = "Bearer ";

    /**
     * Saves the SecurityContext
     *
     * @param exchange the exchange to associate to the SecurityContext
     * @param context  the SecurityContext to save
     * @return a completion notification (success or error)
     */
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Not supported yet. And I don't know what's this");
    }

    /**
     * Loads the SecurityContext associated with the {@link ServerWebExchange}
     *
     * @param exchange the exchange to look up the {@link SecurityContext}
     * @return the {@link SecurityContext} to look up or empty if not found. Never null
     */
    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(authScheme))
                .flatMap(authHeader -> {
                    String authToken = authHeader.substring(authScheme.length());
                    Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);

                    return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
                });
    }
}
