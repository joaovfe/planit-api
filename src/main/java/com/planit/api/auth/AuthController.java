package com.planit.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planit.api.auth.dtos.LoginUserDto;
import com.planit.api.auth.dtos.RecoveryJwtTokenDto;
import com.planit.api.auth.dtos.ResponseUserDto;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = authService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var auth = authService.getAuthenticateUser(username);
        return new ResponseEntity<>(auth, HttpStatus.OK);
    }
}