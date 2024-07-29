package com.example.healthboy.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.http.HttpStatus;

import com.example.healthboy.common.dto.TokenInfo;
import com.example.healthboy.common.enums.SSOType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class ApplicationToken {

    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String tokenId, SSOType ssoType) {

        Date validDate = new Date(System.currentTimeMillis() + 72 * 3600 * 1000); // 3 days validity
        Map<String, Object> claims = new HashMap<>();
        claims.put("ssoType", ssoType);
        claims.put("tokenId", tokenId);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validDate)
                .signWith(key)
                .compact();
    }

    public static TokenInfo decodeToken(String token) {
        Claims claims = null;
        try {// Parse token
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new ApplicationException(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        if (claims == null) {
            throw new ApplicationException("Invalid token", HttpStatus.UNAUTHORIZED);
        }

        String tokenId = (String) claims.get("tokenId");
        SSOType ssoType = Util.safeValueOf(SSOType.class, (String) claims.get("ssoType"));

        if (tokenId == null || ssoType == null) {
            throw new ApplicationException("Invalid token", HttpStatus.UNAUTHORIZED);
        }

        Date validDate = claims.getExpiration();

        if (validDate.before(new Date())) {
            throw new ApplicationException("Token has expired", HttpStatus.UNAUTHORIZED);
        }

        return new TokenInfo(tokenId, ssoType);

    }

}
