package com.example.projectapp.repository;

import com.example.projectapp.persistence.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
