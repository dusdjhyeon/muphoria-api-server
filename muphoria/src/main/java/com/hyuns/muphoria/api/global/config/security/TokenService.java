package com.hyuns.muphoria.api.global.config.security;

import com.hyuns.muphoria.api.domain.user.User;
import com.hyuns.muphoria.api.domain.user.repository.UserRepository;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import io.jsonwebtoken.Claims;

import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${jwt.secret_key}")
    private String secretKey;

    private final UserRepository userRepository;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public User getUserByToken(String token) {
        return userRepository.findById(Integer.valueOf(getUid(token))).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
        );
    }

    public Token generateToken(String uid, String role) {
        long tokenPeriod = 1000L * 60L * 60L * 24L * 14L;  // 2주

        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("role", role);

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return Token.builder().token(accessToken).build();
    }


    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());

        } catch (Exception e) {
            return false;
        }
    }


    public String getUid(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
