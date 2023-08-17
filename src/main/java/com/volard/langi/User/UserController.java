package com.volard.langi.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Saves provided user
     * @param user provided User object
     * @return
     */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<User> save(@RequestBody User user) {
        System.out.println("ashta");return this.userService.save(user);
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

    @GetMapping(value = "/test")
    private User test() {
        User test = new User();
        test.setUsername("asht");
        test.setEmail("fovk u@aesht.at");
        test.setPassword("345678");
        test.setId("34567898888");
        return test;
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
