package leonardo.lil_mart.auth.dto;

import leonardo.lil_mart.user.model.UserRole;

import java.time.LocalDate;

public record RegisterDTO(
        String name,
        String email,
        String password,
        LocalDate birth,
        String cpf,
        UserRole role) {
}
