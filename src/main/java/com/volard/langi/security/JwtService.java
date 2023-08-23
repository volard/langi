package com.volard.langi.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import java.security.KeyPair;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    // todo hide fields in secrets or somewhere and change them
    private final int expirationTimeMinutes = 15;
    private final String audience = "langi_users";
    private final String issuer = "volard";
    private final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    public String createJwt(String userId){
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(userId)
                .setAudience(audience)
                .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(expirationTimeMinutes))))
                .setIssuedAt(Date.from(Instant.now()))
                .setNotBefore(Date.from(Instant.now()))
                .setId(UUID.randomUUID().toString())
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .compact();
    }

    public Jwt readJwt(String jwt){
        JwtParser parser = Jwts.parserBuilder().setSigningKey(keyPair.getPublic()).build();
        return parser.parseClaimsJwt(jwt);
    }
}
