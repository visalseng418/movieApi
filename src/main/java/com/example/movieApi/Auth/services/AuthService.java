package com.example.movieApi.Auth.services;

import com.example.movieApi.Auth.entities.User;
import com.example.movieApi.Auth.entities.UserRole;
import com.example.movieApi.Auth.repositories.UserRepository;
import com.example.movieApi.Auth.services.JwtService;
import com.example.movieApi.Auth.services.RefreshTokenService;
import com.example.movieApi.Auth.utils.AuthResponse;
import com.example.movieApi.Auth.utils.LoginRequest;
import com.example.movieApi.Auth.utils.RegisterRequest;
import com.example.movieApi.Exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;


    //Register and create a new user.
    public AuthResponse register(RegisterRequest registerRequest) {
           var user = User.builder()
                    .name(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .username(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(UserRole.USER)
                    .build();

           User savedUser = userRepository.save(user);
           var accessToken = jwtService.generateToken(savedUser);
           var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());

           return AuthResponse.builder()
                   .accessToken(accessToken)
                   .refreshToken(refreshToken.getRefreshToken())
                   .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new UserNotFoundException("User not found!"));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

        System.out.println("Login succesfully");
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
