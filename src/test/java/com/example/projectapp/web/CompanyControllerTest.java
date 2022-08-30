package com.example.projectapp.web;

import com.example.projectapp.ProjectappApplicationTests;
import com.example.projectapp.persistence.Company;
import com.example.projectapp.service.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class CompanyControllerTest extends ProjectappApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompanyService companyService;

    @BeforeEach
    public void prepare() {
        cleanDb();
        prepareEntities();
    }

    @Test
    public void should_getOneCompanyAfterGetCall() throws Exception {
        Assertions.assertNotNull(objectMapper);

        var result = mockMvc.perform(get("/companies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Company> companies = objectMapper.readValue(result, new TypeReference<>() {});

        Assertions.assertEquals(1, companies.size());
        Assertions.assertEquals(2, companies.get(0).getEmployees().size());
    }

    @Test
    public void should_createNewCompany() throws Exception {
        var company = prepareCompany();
        var employees = prepareEmployees(2);
        company.setEmployees(employees);

        String payload = objectMapper.writeValueAsString(company);

        var response = mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var savedCompany = objectMapper.readValue(response, Company.class);
        Assertions.assertNotNull(savedCompany.getId());
        Assertions.assertEquals(2, savedCompany.getEmployees().size());

        savedCompany.getEmployees().forEach(employee -> Assertions.assertNotNull(employee.getId()));
    }


    private void prepareEntities() {
        var employees = prepareEmployees(2);
        var company = prepareCompany();
        company.setEmployees(employees);

        companyService.save(company);
    }
}