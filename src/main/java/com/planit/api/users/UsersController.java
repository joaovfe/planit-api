package com.planit.api.users;

import com.planit.api.users.dtos.GetUsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping
    public ResponseEntity<List<GetUsersDto>> getUsers() {
        List<GetUsersDto> usuarios = usersService.getUsers();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<GetUsersDto> getUserById(@PathVariable Long id) {
        GetUsersDto usuario = usersService.getUserById(id);
        return ResponseEntity.ok(usuario);
    }
}
