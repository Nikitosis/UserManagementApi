package com.test.service;

import com.test.dao.RoleRepository;
import com.test.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Can't find role by name"));
    }
}
