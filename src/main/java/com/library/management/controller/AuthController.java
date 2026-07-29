package com.library.management.controller;
import com.library.management.dto.AuthResponse;
import com.library.management.dto.LoginRequestDto;
import com.library.management.dto.RegisterRequestDto;
import com.library.management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDto request) {
        String response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequestDto request) {
        AuthResponse requestDto = authService.login(request);
        return ResponseEntity.ok(requestDto);
    }
}
