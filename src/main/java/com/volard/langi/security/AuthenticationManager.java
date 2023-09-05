package com.volard.langi.security;

import com.volard.langi.User.User;
import com.volard.langi.User.UserService;
import com.volard.langi.exception.BadCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Strategy to determine if the provided {@link Authentication} can be authenticated.
 * @see ReactiveAuthenticationManager
 */
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Attempts to authenticate the provided {@link Authentication}
     * @param authentication the {@link Authentication} to test
     * @return if authentication is successful an {@link Authentication} is returned. If
     * authentication cannot be determined, an empty Mono is returned. If authentication
     * fails, a Mono error with {@link BadCredentialsException} is returned.
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        String presentedPassword = (String)authentication.getCredentials();
        return userService.findByUsername(username)
//                .doOnNext(this.preAuthenticationChecks::check)
                .filter(User::isEnabled)
                .filter((userDetails) -> this.passwordEncoder.matches(presentedPassword, userDetails.getPassword()))
                .switchIfEmpty(Mono.error(new BadCredentialsException()))
//                .doOnNext(this.postAuthenticationChecks::check)
                .map(user -> authentication);
    }
}
