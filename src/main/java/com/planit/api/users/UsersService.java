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
    private UserRepository usuarioRepository;

        public List<GetUsersDto> listarUsuarios() {
        List<Users> users = usuarioRepository.findAll();

        return users.stream().map(user -> new GetUsersDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles()
        )).collect(Collectors.toList());
    }
}
