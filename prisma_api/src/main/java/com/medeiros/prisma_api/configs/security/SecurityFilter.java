package com.medeiros.prisma_api.configs.security;


import com.medeiros.prisma_api.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoveryToken(request);

        if (token != null){
            var email = tokenService.validateToken(token);
            UserDetails user = userRepository.findByEmail(email);

            // Cria um objeto para a aplicação saber as permissões do user logado
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);


    };


    // recupera a chave token da requisição
    private String recoveryToken( HttpServletRequest httpServletRequest){
        var authHeader =httpServletRequest.getHeader("Authorization");

        if (authHeader == null) {
            log.error("TOKEN NOT FOUNDED");
            return null;
        }

        log.error("TOKEN IS FOUNDED");
        return authHeader.replace("Bearer ", "");
    }
}
