package com.abulhasan.workforcemanager.controller;

import com.abulhasan.workforcemanager.entity.Employee;
import com.abulhasan.workforcemanager.entity.EmployeeStatus;
import com.abulhasan.workforcemanager.entity.Role;
import com.abulhasan.workforcemanager.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;
    private final PasswordEncoder encoder;

    public EmployeeController(EmployeeService service, PasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @GetMapping
    public String list(@RequestParam(value = "q", required = false) String q, Model model) {
        model.addAttribute("employees", service.list(q));
        model.addAttribute("q", q == null ? "" : q);
        return "employees";
    }

    @GetMapping("/new")
    public String newEmployee(Model model) {
        Employee e = new Employee();
        e.setStatus(EmployeeStatus.ACTIVE);
        e.setRole(Role.USER);

        model.addAttribute("employee", e);
        model.addAttribute("isEdit", false);
        model.addAttribute("statuses", EmployeeStatus.values());
        model.addAttribute("roles", Role.values());
        return "employee-form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("employee") Employee employee,
                         BindingResult result,
                         Model model) {

        if (employee.getPassword() == null || employee.getPassword().isBlank()) {
            result.rejectValue("password", "password.required", "Password is required");
        }

        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            model.addAttribute("statuses", EmployeeStatus.values());
            model.addAttribute("roles", Role.values());
            return "employee-form";
        }

        employee.setPassword(encoder.encode(employee.getPassword()));
        service.create(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("employee", service.get(id));
        return "employee-view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("employee", service.get(id));
        model.addAttribute("isEdit", true);
        model.addAttribute("statuses", EmployeeStatus.values());
        model.addAttribute("roles", Role.values());
        return "employee-form";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("employee") Employee employee,
                         BindingResult result,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            model.addAttribute("statuses", EmployeeStatus.values());
            model.addAttribute("roles", Role.values());
            return "employee-form";
        }

        // If password provided, encode it
        if (employee.getPassword() != null && !employee.getPassword().isBlank()) {
            employee.setPassword(encoder.encode(employee.getPassword()));
        }

        service.update(id, employee);
        return "redirect:/employees/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/employees";
    }
}
