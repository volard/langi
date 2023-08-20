package com.volard.langi.security;

import com.volard.langi.User.User;
import com.volard.langi.User.UserService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public Mono<ResponseEntity<Object>> signup(@RequestBody User user){

        return userService.registerUser(user).flatMap(
                user1 -> {
                    if (user1.equals(null)){
                        return Mono.empty();
                    }
                    return Mono.just(ResponseEntity.ok().build());
                }
                )
                .switchIfEmpty(Mono.just(ResponseEntity.internalServerError().build()));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Void>> login(@RequestBody AuthRequestDto request){

        return userService
                .findByUsername(request.username())
                .filter(user -> Objects.equals(user.getPassword(), request.password()))
                .map(user -> {
                    String jwt = (new JwtService()).createJwt(user.getEmail());
                    // store jwt in the localStorage
                }).switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}