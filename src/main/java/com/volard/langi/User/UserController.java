package com.volard.langi.User;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<User> save(@RequestBody User user) {
        return this.userService.registerUser(user);
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
    @ResponseBody
    private Mono<ResponseEntity<User>> getById(@PathVariable String id){
        Mono<User> test = this.userService.findById(id);

        return test.flatMap(
                user1 -> Mono.just(ResponseEntity.ok().body(user1))
        ).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
