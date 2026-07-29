package com.library.management.service;
import com.library.management.dto.AuthResponse;
import com.library.management.dto.LoginRequestDto;
import com.library.management.dto.RegisterRequestDto;
import com.library.management.model.User;
import com.library.management.repository.UserRepository;
import com.library.management.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public String register(RegisterRequestDto request) {
        validateUsername(request.getUsername());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of("ROLE_USER"));
        userRepository.save(user);

        return "İstifadəçi uğurla qeydiyyatdan keçdi!";
    }

    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Bu istifadəçi adı artıq mövcuddur!");
        }
    }

    public AuthResponse login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();
        String role = "";
        if (user != null && user.getRoles() != null && !user.getRoles().isEmpty()) {
            role = user.getRoles().iterator().next();
        }
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, role);

    }
}
