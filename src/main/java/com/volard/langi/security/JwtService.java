package com.volard.langi.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import java.security.KeyPair;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    private final int timeOffset = 60 * 15;
    private final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    public String createJwt(String userId){
        return Jwts.builder()
                .setIssuer("volard")
                .setSubject(userId)
                .setAudience("you")
                .setExpiration(new Date(System.currentTimeMillis() + timeOffset))
                .setIssuedAt((new Date()))
                .setNotBefore(new Date())
                .setId(UUID.randomUUID().toString())
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .compact();
    }

    public Jwt readJwt(String jwt){
        JwtParser parser = Jwts.parserBuilder().setSigningKey(keyPair.getPublic()).build();
        return parser.parseClaimsJwt(jwt);
    }
}
