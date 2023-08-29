package com.example.securityjwt.service;

import com.example.securityjwt.dto.AuthenticationRequest;
import com.example.securityjwt.dto.AuthenticationResponse;
import com.example.securityjwt.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);
    List<User> findAll();

    void deleteById(Integer userId);

}
