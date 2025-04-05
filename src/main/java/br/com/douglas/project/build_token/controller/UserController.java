package br.com.douglas.project.build_token.controller;

import br.com.douglas.project.build_token.entity.User;
import br.com.douglas.project.build_token.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping(value = "/auth/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email , @RequestHeader("Authorization") String header) {
        var user = userService.getUser(email,header);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping(value = "/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email , @RequestHeader("Authorization") String header) {
        userService.deleteUser(email,header);

        return ResponseEntity.ok().build();
    }



}
