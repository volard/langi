package com.volard.langi.security;

import com.volard.langi.User.User;
import com.volard.langi.User.UserService;
import com.volard.langi.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder encoder;

    @PostMapping("/signup")
    public Mono<User> signup(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> login(@RequestBody AuthRequestDto request) {

        return userService
                .findByUsername(request.username())
                .filter(user -> encoder.matches(request.password(), user.getPassword()))
                .map(user -> {
                    String jwt = jwtService.createJwt(user.getUsername());
                    // todo store jwt in the sessionStorage
                    System.out.printf("JWT for that idiot with id [%s]: %s", user.getId(), jwt);
                    ResponseCookie authCookie = ResponseCookie.fromClientResponse("X-Auth", jwt)
                            .maxAge(3600)
                            .httpOnly(true)
                            .path("/")
                            .secure(false) // should be true in production
                            .build();

                    return ResponseEntity.noContent()
                            .header("Set-Cookie", authCookie.toString())
                            .build();
//                    return ResponseEntity.status(HttpStatus.OK).body(jwt);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));

    }


    @PostMapping("/test")
    public Mono<ResponseEntity<String>> test() {
        throw new UserNotFoundException();
    }
}