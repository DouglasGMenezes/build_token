package br.com.douglas.project.build_token.controller;

import br.com.douglas.project.build_token.dto.LoginUser;
import br.com.douglas.project.build_token.dto.RecordUser;
import br.com.douglas.project.build_token.dto.TokenUser;
import br.com.douglas.project.build_token.entity.User;
import br.com.douglas.project.build_token.service.JWTSevice;
import br.com.douglas.project.build_token.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/api")
public class UserLoginController {

    private UserService userService;
    private JWTSevice jwtSevice;


    @PostMapping(value = "/register")
    public ResponseEntity<User> registerUser(@RequestBody RecordUser recordUser) {
        var user = userService.createUser(recordUser);

        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenUser> authUser(@RequestBody LoginUser loginUser) {
        var token = jwtSevice.authenticateUser(loginUser);

        return ResponseEntity.ok(token);
    }
}
