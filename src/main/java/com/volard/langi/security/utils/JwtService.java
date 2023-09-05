package com.volard.langi.security.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * Service to handle (generate and verify) JWT
 */
@Service
public class JwtService {
    @Value("${application.security.jwt.expiration}")
    private int expirationTimeInSeconds;

    @Value("${application.security.jwt.audience}")
    private String audience;

    @Value("${application.security.jwt.issuer}")
    private String issuer;

    @Value("${application.security.jwt.secret-key}")
    private String secret;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private int refreshExpiration;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

    private final KeyPair keyPair = Keys.keyPairFor(signatureAlgorithm);

    public String createJwt(String userId){

        return Jwts.builder()

                .setIssuer(issuer)
                .setSubject(userId)
                .setAudience(audience)
                .setExpiration(Date.from(Instant.now().plus(Duration.ofSeconds(expirationTimeInSeconds))))
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
