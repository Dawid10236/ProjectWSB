package com.example.projectapp.service;

import com.example.projectapp.persistence.Employee;
import com.example.projectapp.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Company with given id doesn't exist"));
    }

    public List<Employee> saveAll(List<Employee> employees) {
        return employeeRepository.saveAll(employees);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }


    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
}
