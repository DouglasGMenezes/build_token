package br.com.douglas.project.build_token.filter;

import br.com.douglas.project.build_token.service.JWTSevice;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import javax.swing.*;
import java.io.IOException;

public class TokenFilter extends GenericFilterBean {

    public static final int TOKEN_INDEX = 7;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String header = httpServletRequest.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer")) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid or badly formatted token.");
            return;
        }

        String token = header.substring(TOKEN_INDEX);

        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(JWTSevice.TOKEN_KEY).build();
            parser.parseClaimsJws(token).getBody();

        }catch ( SignatureException | MalformedJwtException | UnsupportedJwtException | PrematureJwtException | IllegalArgumentException | ExpiredJwtException e ) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(request,response);
    }
}
