package learn.budget.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import learn.budget.models.AppUser;

import java.security.Key;
import java.util.Date;

@Component
public class JwtConverter {

    private Key signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String buildJwt(AppUser user){
        String token = io.jsonwebtoken.Jwts.builder()
                .setId( user.getUserId() + ""  )
                .claim("email", user.getEmail())
                .claim("roles", user.getUserRoles())
                .setIssuer("budget-controller")
                .setSubject(user.getUsername())
                .setIssuedAt( new Date())
                .setExpiration( new Date( new Date().getTime() + 300000))
                .signWith(signingKey)
                .compact();

        return token;
    }

    public Jws<Claims> parseJwt(String jwt) {

        Jws<Claims> userClaims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build().parseClaimsJws( jwt );

        return userClaims;

    }
}