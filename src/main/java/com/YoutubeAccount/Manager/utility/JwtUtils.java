package com.YoutubeAccount.Manager.utility;

import com.YoutubeAccount.Manager.service.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import static javax.crypto.Cipher.SECRET_KEY;

@Slf4j
@Component
public class JwtUtils {

    @Autowired
    private UserDetailsImpl userDetails;
    private final String SECRETE_KEY="ThisIsMySuperSecretJWTKey1234567890";
    private final long EXPIRATION_TIME = 1000* 60 * 60;//1 Hour expiration time
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRETE_KEY.getBytes());
    }
    // ✅ Generate a token
    public String generateToken(String username) {
        log.info("*** GENERATE TOKEN CALLED for username: {}", username);

        return Jwts.builder()
                .setSubject(username) // subject is the standard place for username/email
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Generic Extractor
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Extract username/email from token
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public boolean isTokenValid(String token){
        try{
             String username1 = extractUsername(token);
            log.info("*** USERNAME FROM EXTRACT: {}",username1);
            UserDetails details = userDetails.loadUserByUsername(username1);
            log.info("*** USERNAME FROM USERDETAILS: {}",details.getUsername());
            return (username1.equals(userDetails.loadUserByUsername(username1).getUsername()) && !isTokenExpire(token));
        } catch (Exception e) {
            log.error("Error occurred: {}",e);
            return false;
        }
    }

    //Check token expiration4
    private boolean isTokenExpire(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
