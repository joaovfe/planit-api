package com.planit.api.users;

import com.planit.api.users.dtos.GetUsersDto;
import com.planit.api.models.Users;
import com.planit.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {
    @Autowired
    private UserRepository userRepository;

        public List<GetUsersDto> getUsers() {
        List<Users> users = userRepository.findAll();

        return users.stream().map(user -> new GetUsersDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles()
        )).collect(Collectors.toList());
    }

    public GetUsersDto getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return new GetUsersDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles()
        );
    }
}
