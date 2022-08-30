package com.example.projectapp.service;

import com.example.projectapp.ProjectappApplicationTests;
import com.example.projectapp.persistence.Company;
import com.example.projectapp.persistence.Employee;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CompanyServiceTest extends ProjectappApplicationTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    @Test
    public void should_saveCompanyWithEmployees() {
        //Given
        var employees = prepareEmployees(5);
        var company = prepareCompany();

        //When
        company.setEmployees(employees);
        company = companyService.save(company);

        //Then
        Assertions.assertNotNull(company.getId());
        company.getEmployees().forEach(employee -> Assertions.assertNotNull(employee.getId()));

    }

    @Test
    public void should_saveCompanyWithListOfEmployees() {
        //Given
        var company = prepareCompany();
        var employeeWithId = prepareEmployee(0);
        var employeeWithoutId = prepareEmployee(1);

        //When
        employeeWithId = employeeService.save(employeeWithId);
        company = companyService.save(company);
        company = companyService.addEmployees(company.getId(), List.of(employeeWithId, employeeWithoutId));
        //Then
        Assertions.assertNotNull(company.getId());
        company.getEmployees().forEach(employee -> Assertions.assertNotNull(employee.getId()));
        Assertions.assertTrue(company.getEmployees().contains(employeeWithId));

    }
}