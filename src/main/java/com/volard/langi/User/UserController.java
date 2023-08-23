package com.volard.langi.User;

import com.volard.langi.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @DeleteMapping("/users/{id}")
    private Mono<ResponseEntity<String>> delete(@PathVariable("id") String id) {
        return this.userService.delete(id)
                .flatMap(user -> Mono.just(ResponseEntity.ok(String.format("User under id [%s] Deleted Successfully", id))))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/users/{id}")
    private Mono<ResponseEntity<User>> update(@PathVariable("id") String id, @RequestBody User user) {
        return this.userService.update(id, user)
                .flatMap(
                        user1 -> Mono.just(ResponseEntity.ok(user1))
                ).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping(value = "/users")
    private Flux<User> findAll() {
        return this.userService.findAll();
    }




    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/decks")
    private String test() {
        return "some user's decks";
    }

    @GetMapping(value = "/users/{id}")
    private Mono<ResponseEntity<User>> getById(@PathVariable String id){
        return this.userService.findById(id).flatMap(
                user1 -> Mono.just(ResponseEntity.ok().body(user1))
        ).switchIfEmpty(Mono.error(new UserNotFoundException()));
    }
}
