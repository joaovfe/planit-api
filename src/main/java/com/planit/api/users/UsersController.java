package com.planit.api.users;

import com.planit.api.users.dtos.GetUsersDto;
import com.planit.api.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.planit.api.users.UsersService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/get")
    public ResponseEntity<List<GetUsersDto>> listarUsuarios() {
        List<GetUsersDto> usuarios = usersService.listarUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);

    }
}
