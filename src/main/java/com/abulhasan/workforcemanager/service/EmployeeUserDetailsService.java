package com.abulhasan.workforcemanager.service;

import com.abulhasan.workforcemanager.entity.Employee;
import com.abulhasan.workforcemanager.repository.EmployeeRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee emp = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        // Spring Security expects roles like ROLE_ADMIN, ROLE_HR...
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + emp.getRole().name()));

        return User.builder()
                .username(emp.getEmail())
                .password(emp.getPassword()) // BCrypt hash stored in DB
                .authorities(authorities)
                .disabled(emp.getStatus() != null && emp.getStatus().name().equals("INACTIVE"))
                .build();
    }
}
