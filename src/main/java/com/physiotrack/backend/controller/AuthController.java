package com.physiotrack.backend.controller;

import com.physiotrack.backend.model.auth.dto.AuthRequest;
import com.physiotrack.backend.model.auth.dto.AuthResponse;
import com.physiotrack.backend.model.auth.dto.AuthRole;
import com.physiotrack.backend.model.enums.Role;
import com.physiotrack.backend.model.pessoa.Pessoa;
import com.physiotrack.backend.model.user.User;
import com.physiotrack.backend.model.user.UserRegisterDTO;
import com.physiotrack.backend.repository.UserRepository;
import com.physiotrack.backend.config.security.JwtService;
import com.physiotrack.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    /*
    endpoint deve ser usado somente para gerar usuario do fisioterapeuta
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegisterDTO dto) {
        userService.register(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthRole> getLoggedUser() {
        User user = userService.getLoggedUser();
        return ResponseEntity.ok(new AuthRole(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getTipoUsuario()
        ));
    }
}
