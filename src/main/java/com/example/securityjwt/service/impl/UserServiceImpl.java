package com.example.securityjwt.service.impl;

import com.example.securityjwt.config.JwtUtils;
import com.example.securityjwt.dto.AuthenticationRequest;
import com.example.securityjwt.dto.AuthenticationResponse;
import com.example.securityjwt.entity.User;
import com.example.securityjwt.repository.UserRepository;
import com.example.securityjwt.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        final User user;
        // CHECK MAIL
        try{
            user= (User) userRepository.findByEmail(request.getEmail()).get();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }

        // CHECK Auth Mdp
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        } catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }

        // Si ok
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getPrenom() + " " + user.getNom());
        final String token = jwtUtils.generateToken(user,claims);

        return ResponseEntity.ok( AuthenticationResponse.builder().token(token).build());
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Integer userId) {
        userRepository.deleteById(userId);
    }
}
