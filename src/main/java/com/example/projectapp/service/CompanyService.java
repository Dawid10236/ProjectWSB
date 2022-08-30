package com.example.projectapp.service;

import com.example.projectapp.persistence.Company;
import com.example.projectapp.persistence.Employee;
import com.example.projectapp.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final EmployeeService employeeService;

    public Company findById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Company with given id doesn't exist"));
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    private boolean hasAnyUnsavedEmployee(Company company) {
        return company.getEmployees().stream().filter(employee -> employee.getId() == null)
                .toList().size() > 0;
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    public Company addEmployee(Long companyId, Employee employee) {
        var company = findById(companyId);
        company.getEmployees().add(employee);
        return save(company);
    }

    @Transactional
    public Company addEmployees(Long companyId, List<Employee> employees) {
        var company = findById(companyId);
        company.getEmployees().addAll(employees);
        return save(company);
    }
}
