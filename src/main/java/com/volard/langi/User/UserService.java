package com.volard.langi.User;

import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsPasswordService {
    Mono<User> registerUser(User user);
    Mono<User> delete(String id);
    Mono<User> update(String id, User user);
    Flux<User> findAll();
    Mono<User> findById(String id);
    Mono<User> findByUsername(String username);
}