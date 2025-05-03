package com.planit.api.auth;

import com.planit.api.auth.dtos.CreateUserDto;
import com.planit.api.auth.dtos.LoginUserDto;
import com.planit.api.auth.dtos.RecoveryJwtTokenDto;
import com.planit.api.auth.dtos.UserReponseDto;
import com.planit.api.configuration.SecurityConfiguration;
import com.planit.api.models.Role;
import com.planit.api.repositories.UserRepository;
import com.planit.api.security.JwtTokenService;
import com.planit.api.models.Users;
import com.planit.api.security.userdetailimp.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {
        Optional<Users> usuarioEmailRepetido =  userRepository.findByEmail(createUserDto.email());
        if(usuarioEmailRepetido.isPresent()){
            throw new RuntimeException("Já possui um usuário com o email informado");
        }
        Users newUser = Users.builder()
                .name(createUserDto.name())
                .email(createUserDto.email())
                .password_hash(securityConfiguration.passwordEncoder().encode(createUserDto.password_hash()))
                .roles(List.of(Role.builder().roleName(createUserDto.role()).build()))
                .build();

        userRepository.save(newUser);
    }

    public UserReponseDto getAuthenticateUser(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return new UserReponseDto(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles());
    }

}
