package com.dataart.springtraining.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by kimeshkov on 28.01.2016.
 */
public class JWTAuthenticationFilter extends GenericFilterBean {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String HEADER_PREFIX = "Bearer ";

    private final String secret;

    public JWTAuthenticationFilter(String secret) {
        this.secret = secret;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = getAsHttpRequest(req);
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authHeader != null && authHeader.startsWith(HEADER_PREFIX)) {
            // The part after "Bearer "
            String token = authHeader.substring(HEADER_PREFIX.length());

            Optional<Claims> claims = getClaims(token);
            if (claims.isPresent()) {
                authenticateUser(claims.get());
            }
        }
        chain.doFilter(req, res);
    }

    private HttpServletRequest getAsHttpRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }

        return (HttpServletRequest) request;
    }

    private Optional<Claims> getClaims(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return Optional.of(claims);
        } catch (final SignatureException e) {
            return Optional.empty();
        }
    }

    private void authenticateUser(Claims claims) {

        /*UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);*/
    }

    private static class MyToken extends AbstractAuthenticationToken {

        public MyToken(Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }
    }
}
