package com.planit.api.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planit.api.trips.dtos.CreateTripDto;
import com.planit.api.users.dtos.CreateUserDto;
import com.planit.api.users.dtos.GetUsersDto;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody CreateUserDto createUserDto) {
        usersService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUsers(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "10") int take,
            @RequestParam(required = false, defaultValue = "0") int skip) {
        List<GetUsersDto> users = usersService.getUsers();

        long total = users.size();
        int pages = (int) Math.ceil((double) total / take);

        Map<String, Object> response = new HashMap<>();
        response.put("data", users);
        response.put("total", total);
        response.put("pages", pages);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody CreateUserDto dto) {

        usersService.updateUser(id, dto);
        return ResponseEntity.ok("Usuário atualizado com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUsersDto> getUserById(@PathVariable Long id) {
        GetUsersDto usuario = usersService.getUserById(id);
        return ResponseEntity.ok(usuario);
    }
}
