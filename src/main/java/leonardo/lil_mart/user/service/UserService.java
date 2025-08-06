package leonardo.lil_mart.user.service;

import leonardo.lil_mart.auth.dto.RegisterDTO;
import leonardo.lil_mart.user.model.User;
import leonardo.lil_mart.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService{
    @Autowired
    UserRepository userRepository;

    public User register(RegisterDTO registerDTO) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        User user = new User(registerDTO.name(),
                            registerDTO.email(),
                            encryptedPassword,
                            registerDTO.birth(),
                            registerDTO.cpf(),
                            registerDTO.role(),
                            LocalDateTime.now(),
                            LocalDateTime.now());

        return userRepository.save(user);
    }
}