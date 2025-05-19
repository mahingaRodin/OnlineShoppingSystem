package com.onlineShoppingBe.beForOnlineShoppingSystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    public String extractUsername(String token) throws BadRequestException {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws BadRequestException {
        final Claims claims = extralAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extralAllClaims(String token) throws BadRequestException {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigninkey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new BadRequestException(" please provide valid token " + e.getMessage());
        } catch (JwtException e) {
            throw new BadRequestException("token parsing failed  " + e.getMessage());

        }


    }

    private Key getSigninkey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) throws BadRequestException {
        final String username = extractUsername(token);

        return (Objects.equals(username, userDetails.getUsername()) && validateToken(token));
    }

    private boolean validateToken(String token) throws BadRequestException {
        final Date expirationDate = extractClaim(token, Claims::getExpiration);
        return expirationDate.after(new Date());
    }
}
