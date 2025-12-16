package com.abulhasan.workforcemanager.service;

import com.abulhasan.workforcemanager.entity.Employee;
import com.abulhasan.workforcemanager.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public List<Employee> list(String q) {
        if (!StringUtils.hasText(q)) {
            return repo.findAll();
        }
        String query = q.trim();
        return repo.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartmentContainingIgnoreCaseOrDesignationContainingIgnoreCase(
                query, query, query, query
        );
    }

    @SuppressWarnings("null")
    public Employee get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + id));
    }

    @SuppressWarnings("null")
    public Employee create(Employee employee) {
        return repo.save(employee);
    }

    public Employee update(Long id, Employee updated) {
        Employee existing = get(id);

        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setDepartment(updated.getDepartment());
        existing.setDesignation(updated.getDesignation());
        existing.setSalary(updated.getSalary());
        existing.setStatus(updated.getStatus());
        existing.setRole(updated.getRole());

        // Password: only update if user typed something
        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            existing.setPassword(updated.getPassword()); // controller will encode before saving
        }

        return repo.save(existing);
    }

    @SuppressWarnings("null")
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
