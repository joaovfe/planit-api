package com.planit.api.roles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.api.models.RoleModel;
import com.planit.api.repositories.RoleRepository;
import com.planit.api.roles.dtos.RoleResponseDto;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleResponseDto> getAllRoles() {
        List<RoleModel> roles = roleRepository.findAll();
    
        return roles.stream()
                .map(role -> RoleResponseDto.builder()
                        .id(role.getId())
                        .roleName(role.getRoleName())
                        .build()).toList();
    }
}
