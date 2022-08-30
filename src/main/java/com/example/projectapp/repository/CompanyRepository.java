package com.example.projectapp.repository;

import com.example.projectapp.persistence.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
