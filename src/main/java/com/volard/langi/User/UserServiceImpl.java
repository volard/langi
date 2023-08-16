package com.volard.langi.User;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    // TODO remove after experiments
    private static final int DELAY_PER_ITEM_MS = 200;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public Mono<User> save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Mono<User> delete(String id) {
        return this.userRepository
                .findById(id).flatMap(p ->
                        this.userRepository
                                .deleteById(p.getId())
                                .thenReturn(p));
    }

    @Override
    public Mono<User> update(String id, User user) {
        return null;
    }

    @Override
    public Flux<User> findAll() {
        // delay is for experimental purposes for now
        // TODO remove delay after experiments
        return userRepository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
    }

    @Override
    public Mono<User> findById(String id) {
        return this.userRepository.findById(id);
    }
}
