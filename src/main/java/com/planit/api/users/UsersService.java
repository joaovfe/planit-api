package com.planit.api.users;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.api.configuration.SecurityConfiguration;
import com.planit.api.configuration.exception.ValidationException;
import com.planit.api.models.Role;
import com.planit.api.models.TripModel;
import com.planit.api.models.Users;
import com.planit.api.repositories.TripRepository;
import com.planit.api.repositories.UserRepository;
import com.planit.api.users.dtos.CreateUserDto;
import com.planit.api.users.dtos.GetUsersDto;

@Service
public class UsersService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private TripRepository tripRepository;

    public List<GetUsersDto> getUsers() {
        List<Users> users = userRepository.findAll();

        return users.stream().map(user -> new GetUsersDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRoles(),
                user.getClimatePreference(),
                user.getSeasonPreference(),
                user.getCountryDesired())).collect(Collectors.toList());
    }

    public GetUsersDto getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return new GetUsersDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRoles(),
                user.getClimatePreference(),
                user.getSeasonPreference(),
                user.getCountryDesired());
    }

    public void createUser(CreateUserDto createUserDto) {
        Optional<Users> usuarioEmailRepetido = userRepository.findByEmail(createUserDto.email());
        if (usuarioEmailRepetido.isPresent()) {
            throw new ValidationException("Já possui um usuário com o email informado");
        }

        Users newUser = Users.builder()
                .name(createUserDto.name())
                .email(createUserDto.email())
                .passwordHash(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                .climatePreference(createUserDto.climatePreference())
                .roles(List.of(Role.builder().roleName(createUserDto.role()).build()))
                .seasonPreference(createUserDto.seasonPreference())
                .countryDesired(createUserDto.countryDesired())
                .build();

        userRepository.save(newUser);
    }

    public void updateUser(Long id, CreateUserDto dto) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada"));

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPasswordHash(securityConfiguration.passwordEncoder().encode(dto.password()));
        user.setClimatePreference(dto.climatePreference());
        user.setSeasonPreference(dto.seasonPreference());
        user.setCountryDesired(dto.countryDesired());

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        boolean hasTrips = tripRepository.existsByUserId(id);

        if (hasTrips) {
            throw new IllegalStateException(
                    "Este usuário possui viagens vinculadas. Exclua as viagens antes de remover o usuário.");
        }

        userRepository.delete(user);
    }

}
