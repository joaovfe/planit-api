package com.planit.api.security;


import com.planit.api.repositories.UserRepository;
import com.planit.api.models.Users;
import com.planit.api.security.userdetailimp.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    private Users user;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new UserDetailImpl(user);
    }

    public String getUsuario() {
        return user.toString();
    }
}
