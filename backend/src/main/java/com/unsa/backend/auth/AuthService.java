package com.unsa.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unsa.backend.jwt.JwtService;
import com.unsa.backend.users.Role;
import com.unsa.backend.users.UserModel;
import com.unsa.backend.users.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthUserResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(userDetails);
        return AuthUserResponse.from((UserModel) userDetails, token);
        // return AuthResponse.builder()
        //         .token(token)
        //         .username(user.getUsername())
        //         .firstname(user.getFirstname())
        //         .lastname(user.getLastname())
        //         .build();
    }   
    public AuthResponse register(RegisterRequest request){
        UserModel user = UserModel.builder()
                            .username(request.getUsername())
                            .password(passwordEncoder.encode( request.getPassword() ))
                            .firstname(request.getFirstname())
                            .lastname(request.getLastname())
                            .role(Role.USER)
                            .build();
        
        userRepository.save(user);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();
    }
}
