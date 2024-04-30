package it.academy.components;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import it.academy.models.User;
import lombok.NonNull;

import javax.crypto.SecretKey;
import java.io.InvalidClassException;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static it.academy.utilities.Constants.*;

public class JwtProvider {
    private final SecretKey jwtAccessKey;
    private final SecretKey jwtRefreshKey;
    private final Date jwtAccessExpiration;
    private final Date jwtRefreshExpiration;

    public JwtProvider() {
        this.jwtAccessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_ACCESS_SECRET));
        this.jwtRefreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_REFRESH_SECRET));
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(JWT_ACCESS_EXPIRATION)
                .atZone(ZoneId.systemDefault()).toInstant();
        final Instant refreshExpirationInstant = now.plusDays(JWT_REFRESH_EXPIRATION)
                .atZone(ZoneId.systemDefault()).toInstant();
        this.jwtAccessExpiration = Date.from(accessExpirationInstant);
        this.jwtRefreshExpiration = Date.from(refreshExpirationInstant);
    }

    public String generateAccessToken(@NonNull User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(jwtAccessExpiration)
                .signWith(jwtAccessKey)
                .claim(ROLES_KEY, user.getRoleSet())
                .compact();
    }

    public String generateRefreshToken(@NonNull User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(jwtRefreshExpiration)
                .signWith(jwtRefreshKey)
                .compact();
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            if (secret == jwtAccessKey) {
                Jwts.parserBuilder()
                        .setSigningKey(secret)
                        .build()
                        .parseClaimsJws(token);
            } else if (secret == jwtRefreshKey) {
                Jwts.parserBuilder()
                        .setSigningKey(secret)
                        .build();
            } else {
                throw new InvalidClassException(WRONG_SECRET_KEY);
            }
            return true;
        } catch (ExpiredJwtException expEx) {
            //log.error(TOKEN_ERROR, expEx);
        } catch (UnsupportedJwtException unsEx) {
//            log.error(UNSUPPORTED_JWT_ERROR, unsEx);
        } catch (MalformedJwtException mjEx) {
//            log.error(MALFORMED_JWT_ERROR, mjEx);
        } catch (SignatureException sEx) {
//            log.error(SIGNATURE_ERROR, sEx);
        } catch (Exception e) {
//            log.error(INVALID_TOKEN_ERROR, e);
        }
        return false;
    }

    public boolean validateAccessToken(@NonNull String token) {
        return validateToken(token, jwtAccessKey);
    }

    public boolean validateRefreshToken(@NonNull String token) {
        return validateToken(token, jwtRefreshKey);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessKey);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshKey);
    }

}

