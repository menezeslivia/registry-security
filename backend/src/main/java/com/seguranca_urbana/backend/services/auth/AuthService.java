package com.seguranca_urbana.backend.services.auth;

import com.seguranca_urbana.backend.models.dtos.auth.AuthResponseDTO;
import com.seguranca_urbana.backend.models.dtos.auth.LoginRequestDTO;
import com.seguranca_urbana.backend.models.dtos.auth.RegisterRequestDTO;
import com.seguranca_urbana.backend.models.enums.UserRole;
import com.seguranca_urbana.backend.models.user.User;
import com.seguranca_urbana.backend.repositorys.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("Nome de usuário já cadastrado");
        }
        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .userRole(UserRole.valueOf(request.userRole()))
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token, user.getUsername(), user.getUserRole().name());
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Senha inválida");
        }
        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token, user.getUsername(), user.getUserRole().name());
    }
}