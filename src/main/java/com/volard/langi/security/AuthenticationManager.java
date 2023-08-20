package com.volard.langi.security;

import com.volard.langi.User.User;
import com.volard.langi.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserService userService;

    // determine if the provided Authentication can be authenticated
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return userService.findById(principal.getId())
                .filter(User::isEnabled)
                .switchIfEmpty(Mono.error(new Exception("haha loser")))
                .map(user -> authentication);
    }
}
