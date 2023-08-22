package com.volard.langi.security;

import com.volard.langi.User.User;
import com.volard.langi.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public Mono<ResponseEntity<User>> signup(@RequestBody User user){

        return userService.registerUser(user).flatMap(
                user1 -> {
                    System.out.printf(
                            "User with username [%s] and password [%s] was registered successfully under [%s] id%n",
                            user1.getUsername(), user1.getPassword(), user1.getId());
                    return Mono.just(new ResponseEntity<User>(user1, HttpStatus.CREATED));
                });
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody AuthRequestDto request){

        return userService
                .findByUsername(request.username())
                .filter(user -> Objects.equals(user.getPassword(), request.password()))
                .map(user -> {
                    String jwt = (new JwtService()).createJwt(user.getEmail());
                    // todo store jwt in the sessionStorage
                    return ResponseEntity.status(HttpStatus.OK).body(jwt);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}