package com.example.securityjwt.Controller;

import com.example.securityjwt.entity.User;
import com.example.securityjwt.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/{user-id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Void> deleteById(@PathVariable("user-id") Integer userId){
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }

}
