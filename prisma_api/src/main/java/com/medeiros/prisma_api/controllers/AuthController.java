package com.medeiros.prisma_api.controllers;

import com.medeiros.prisma_api.configs.security.TokenService;
import com.medeiros.prisma_api.domains.user.*;
import com.medeiros.prisma_api.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    private String loginToken(String email, String password){
        // cria uma chave de authenticação com o email e senha do usuário
        var userKey = new UsernamePasswordAuthenticationToken(email, password);
        var auth = this.authenticationManager.authenticate(userKey);

        var token = tokenService.generateToken((User) Objects.requireNonNull(auth.getPrincipal()));
        return token;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto){

        var token = loginToken(dto.email(), dto.password());
        return ResponseEntity.ok().body(new LoginResponseDTO(token));
    }



    @PostMapping("/register")
    public  ResponseEntity<UserResponseDTO> register (@RequestBody @Valid UserRequestDTO dto){

        if (userService.isExist(dto.email())) return ResponseEntity.badRequest().build();

        UserResponseDTO user = this.userService.create(dto);

        return ResponseEntity.ok().body(user);
    }
}
