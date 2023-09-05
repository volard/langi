package com.volard.langi.security;

// todo delete it since Spring Security will handle all requests
//@RestController
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final UserService userService;
//    private final JwtService jwtService;
//    private final PasswordEncoder encoder;
//
//    @PostMapping("/signup")
//    public Mono<User> signup(@RequestBody User user) {
//        return userService.registerUser(user);
//    }
//
//    @PostMapping("/login")
//    public Mono<ResponseEntity<Object>> login(@RequestBody AuthRequestDto request) {
//
//        return userService
//                .findByUsername(request.username())
//                .filter(user -> encoder.matches(request.password(), user.getPassword()))
//                .map(user -> {
//                    String jwt = jwtService.createJwt(user.getUsername());
//                    // todo store jwt in the sessionStorage
//                    System.out.printf("JWT for that idiot with id [%s]: %s", user.getId(), jwt);
//                    ResponseCookie authCookie = ResponseCookie.fromClientResponse("X-Auth", jwt)
//                            .maxAge(3600)
//                            .httpOnly(true)
//                            .path("/")
//                            .secure(false) // should be true in production
//                            .build();
//
//                    return ResponseEntity.noContent()
//                            .header("Set-Cookie", authCookie.toString())
//                            .build();
//                })
//                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
//
//    }
//
//}