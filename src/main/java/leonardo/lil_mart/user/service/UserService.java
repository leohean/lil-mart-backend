package leonardo.lil_mart.user.service;

import leonardo.lil_mart.auth.dto.RegisterDTO;
import leonardo.lil_mart.user.model.User;
import leonardo.lil_mart.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService{
    @Autowired
    UserRepository userRepository;

    public ResponseEntity register(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.name(),
                            registerDTO.email(),
                            encryptedPassword,
                            registerDTO.birth(),
                            registerDTO.cpf(),
                            registerDTO.role(),
                            LocalDateTime.now(),
                            LocalDateTime.now());

        this.userRepository.save(newUser);


        return ResponseEntity.ok().build();
    }
}