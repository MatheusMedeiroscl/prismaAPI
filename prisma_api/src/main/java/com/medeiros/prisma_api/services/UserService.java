package com.medeiros.prisma_api.services;

import com.medeiros.prisma_api.domains.user.User;
import com.medeiros.prisma_api.domains.user.UserRequestDTO;
import com.medeiros.prisma_api.domains.user.UserResponseDTO;
import com.medeiros.prisma_api.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponseDTO findById(Long id){
        User user = this.repository.findById(id).orElseThrow(() -> new RuntimeException(
                "USER NOT FOUNDED BY ID [ " + id + " ]"
        ));

        return new UserResponseDTO(user);
    }

    public Boolean isExist(String email){return this.repository.existsByEmail(email);}

    public UserResponseDTO create(UserRequestDTO dto){
        var encrypt = new BCryptPasswordEncoder().encode(dto.password());

        User user = new User(dto, encrypt);
        this.repository.save(user);

        return new UserResponseDTO(user);
    }



}
