package com.physiotrack.backend.service;


import com.physiotrack.backend.config.security.JwtService;
import com.physiotrack.backend.exceptions.ObjectNotFoundException;
import com.physiotrack.backend.model.auth.dto.AuthRequest;
import com.physiotrack.backend.model.auth.dto.AuthResponse;
import com.physiotrack.backend.model.cidade.Cidade;
import com.physiotrack.backend.model.endereco.Endereco;
import com.physiotrack.backend.model.enums.Role;
import com.physiotrack.backend.model.estado.Estado;
import com.physiotrack.backend.model.pessoa.Pessoa;
import com.physiotrack.backend.model.pessoa.PessoaRequestDTO;
import com.physiotrack.backend.model.user.User;
import com.physiotrack.backend.model.user.UserRegisterDTO;
import com.physiotrack.backend.repository.CidadeRepository;
import com.physiotrack.backend.repository.EnderecoRepository;
import com.physiotrack.backend.repository.EstadoRepository;
import com.physiotrack.backend.repository.PessoaRepository;
import com.physiotrack.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final PessoaService pessoaService;

    @Transactional
    public void register(UserRegisterDTO dto) {
        //
        Pessoa pessoa = pessoaService.register(dto.getPessoa());
        //
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .tipoUsuario("Usuario")
                .pessoa(pessoa)
                .build();
        //
        userRepository.save(user);

        /*String token = jwtService.generateToken(user);*/
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
