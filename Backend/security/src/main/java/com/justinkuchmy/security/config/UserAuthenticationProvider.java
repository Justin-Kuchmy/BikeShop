package com.justinkuchmy.security.config;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider {
     public String secretKey = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String generateToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName);
    }
    
    private String createToken(Map<String, Object> claims, String userName) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour
        var token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
        return token;
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    private Claims extractAllClaims(String token) {
         var res = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return res;
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
         var res = extractClaim(token, Claims::getExpiration);
         return res;
    }
    
    private Boolean isTokenExpired(String token) {
        var res = extractExpiration(token).before(new Date());
        return res;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        var res = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        return res;
    }

    public String extractUsername(String token) {
        var res = extractClaim(token, Claims::getSubject);
        return res;
    }
}
