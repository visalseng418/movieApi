package com.example.movieApi.controllers;

import com.example.movieApi.Auth.entities.RefreshToken;
import com.example.movieApi.Auth.entities.User;
import com.example.movieApi.Auth.services.AuthService;
import com.example.movieApi.Auth.services.JwtService;
import com.example.movieApi.Auth.services.RefreshTokenService;
import com.example.movieApi.Auth.utils.AuthResponse;
import com.example.movieApi.Auth.utils.LoginRequest;
import com.example.movieApi.Auth.utils.RefreshTokenRequest;
import com.example.movieApi.Auth.utils.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        System.out.println("Endpoint hit");
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());

    }


    @PostMapping("/createRefresh")
    public ResponseEntity<RefreshToken> createRefreshTokenForExistingUser(@RequestParam String email) {
        System.out.println("Endpoint hit");
        return ResponseEntity.ok(refreshTokenService.createRefreshToken(email));
    }
}
