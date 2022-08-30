package com.example.projectapp;

import com.example.projectapp.persistence.Company;
import com.example.projectapp.persistence.Employee;
import com.example.projectapp.repository.CompanyRepository;
import com.example.projectapp.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Profile(value = "test")
public class ProjectappApplicationTests {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    protected void cleanDb() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    protected Company prepareCompany() {
        return Company.builder()
                .withName("Test company")
                .withEmployees(new ArrayList<>())
                .build();
    }

    protected List<Employee> prepareEmployees(int employeeCounter) {
        return IntStream.rangeClosed(1, employeeCounter)
                .mapToObj(this::prepareEmployee)
                .collect(Collectors.toList());
    }

    protected Employee prepareEmployee(int counter) {
        return Employee.builder()
                .withFirstName("First name " + counter)
                .withSecondName("Second name " + counter)
                .withSalary(new BigDecimal(counter + ".00"))
                .build();
    }
}
