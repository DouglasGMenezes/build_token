package br.com.douglas.project.build_token.service;

import br.com.douglas.project.build_token.dto.LoginUser;
import br.com.douglas.project.build_token.dto.TokenUser;
import br.com.douglas.project.build_token.entity.User;
import br.com.douglas.project.build_token.repository.UserRepository;
import br.com.douglas.project.build_token.service.exception.InvalidLoginException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class JWTSevice {

    public static final Key TOKEN_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private UserRepository userRepository;

    public JWTSevice(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public TokenUser authenticateUser(LoginUser loginUser){
        Optional<User> user = userRepository.findByEmailAndPassword(loginUser.email(), loginUser.password());
        if (user.isEmpty()) {
            throw new InvalidLoginException("Login inválido.");
        }
        String token = generateToken(loginUser.email());
        TokenUser userWithToken = new TokenUser(token);

        return userWithToken;
    }


    private String generateToken(String email) {
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(email)
                .signWith(TOKEN_KEY,SignatureAlgorithm.HS512)
                .setExpiration(timeToken())
                .compact();
    }


    private Date timeToken() {
        return Date.from(Instant.now().plusSeconds(60 * 5));
    }


    public String getUserByToken(String header) {
        header = header.trim();
        if (!header.startsWith("Bearer ")) {
            throw new SecurityException("Token inválido.");
        }
        String token = header.substring(7);
        String user;
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(TOKEN_KEY).build();
            user = parser.parseClaimsJws(token).getBody().getSubject();
        }catch (SecurityException e) {
            throw new SecurityException("Erro na validação do token.");
        }
        return user;
    }


}
