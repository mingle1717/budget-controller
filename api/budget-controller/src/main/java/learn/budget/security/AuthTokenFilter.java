package learn.budget.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import learn.budget.models.AppUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    UserDetailsServiceImpl service;

    @Autowired
    JwtConverter converter;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {


        String jwt = readToken(request.getHeader("Authorization"));

        if (jwt == null) {
            //user did not send a token
            //they could be logging in
            //or maybe accessing an endpoint that doesn't require
            //authentication
            SecurityContextHolder.getContext().setAuthentication(null);
        } else {
            //otherwise we have a token we can decode and then
            //validate
            Jws<Claims> claims = converter.parseJwt(jwt);

            String userName = claims.getBody().getSubject();

            UserDetails matchingUser = service.loadUserByUsername(userName);

            UsernamePasswordAuthenticationToken rawToken =
                    new UsernamePasswordAuthenticationToken(
                            matchingUser,
                            null,
                            matchingUser.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(rawToken);
        }


        filterChain.doFilter(request, response);


    }

    private String readToken(String authorization) {

        //we get a null if there is no Authorization header
        if (authorization != null) {
            if (authorization.startsWith("Bearer ")) {
                return authorization.substring(7);
            }
        }

        return null;
    }
}


