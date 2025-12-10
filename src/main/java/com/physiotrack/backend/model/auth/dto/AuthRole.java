package com.physiotrack.backend.model.auth.dto;

import com.physiotrack.backend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRole {
    private Long id;
    private String email;
    private Role role;
    private String tipoUsuario;
}
