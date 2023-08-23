package com.volard.langi.User;

import com.volard.langi.exception.UserAlreadyExistsException;
import com.volard.langi.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // TODO remove after experiments
    private static final int DELAY_PER_ITEM_MS = 1000;


    @Override
    public Mono<User> registerUser(User user) {
//        user.setAccountPassword(passwordEncoder.encode(user.getAccountPassword()));
//        return this.userRepository.save(user);

        return userRepository.findByUsername(user.getUsername())
                .flatMap(__ -> Mono.error(new UserAlreadyExistsException()))
                .switchIfEmpty(
                        Mono.defer(() -> {
                            user.setAccountPassword(passwordEncoder.encode(user.getAccountPassword()));
                            return this.userRepository.save(user);
                        })
                ).cast(User.class);
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
        // TODO remove after experiments
        return userRepository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
    }

    @Override
    public Mono<User> findById(String id) {
        return this.userRepository.findById(id);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        // todo implement
//        return this.userRepository.findUserByUsername(username);

        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UserNotFoundException()));
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        // todo implement
        return null;
    }
}
