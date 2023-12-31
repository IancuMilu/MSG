package com.msglearning.javabackend.services;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

    private static final String ROLE = "role";
    private static final String NAME = "name";
    private static final String ID = "id";
    private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
    static final byte[] secretBytes = secret.getEncoded();
    private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);
    private static final String TOKEN_PREFIX = "{\"token\":\"";
    private static final String TOKEN_SUFFIX = "\"}";

    /**
     * Creates a token from the given email, role, and user ID.
     *
     * @param email  - the email of the user
     * @param role   - the role of the user
     * @param userId - the ID of the user
     * @return the generated token
     */
    public String createTokenHeader(final String email, final String role, final String userId) {
        String token = this.createJWT(email, role, userId);
        return TOKEN_PREFIX + token + TOKEN_SUFFIX;
    }

    /**
     * Generates a JWT token from the given parameters.
     *
     * @return the generated token
     */
    private String createJWT(String name, String role, String userId) {
        // The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder().setId(userId).setIssuedAt(now).claim(NAME, name).claim(ROLE, role)
                .signWith(signatureAlgorithm, base64SecretBytes);

        long expMillis = nowMillis + (long) 21600000;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);

        return builder.compact();
    }

    /**
     * Checks if the given token is valid or not.
     *
     * @param token the token to check
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(token);
            Claims claimsBody = claims.getBody();
            StringBuilder toLog = new StringBuilder();
            logClaims(claimsBody, toLog);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException e) {
            LOG.error("Access denied: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Retrieves the name of the user from the token.
     *
     * @param token
     * @return the name of the user
     */
    public String getUserName(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(token);
        Claims claimsBody = claims.getBody();
        return claimsBody.get(NAME).toString();
    }

    private void logClaims(Claims claimsBody, StringBuilder toLog) {
        String separator = System.getProperty("line.separator");
        toLog.append(separator);
        toLog.append("ID: ").append(claimsBody.getId()).append(separator);
        toLog.append("Name: ").append(claimsBody.get(NAME)).append(separator);
        toLog.append("Role: ").append(claimsBody.get(ROLE)).append(separator);
        toLog.append("Expiration: ").append(claimsBody.getExpiration()).append(separator);
        LOG.debug(toLog.toString());
    }

    public String resolveToken(final String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            return getUserName(token);
        }
        return null;
    }
}

