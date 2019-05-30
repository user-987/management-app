package com.example.api.management;

import com.example.api.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeManagementControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private EmployeeManagementService employeeService;

    private Employee employee;

    @Before
    public void setUp() throws Exception
    {
        employee = new Employee("Hello", "World", 1, 1);
    }

    @Test
    public void shouldTestGetAllEmployeesWhenNoEmployees() throws Exception
    {
        given(employeeService.getAll()).willReturn(emptyList());

        mockMvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("[]"));
    }

    @Test
    public void shouldGetAllEmployees() throws Exception
    {
        List<Employee> employeeResult = new ArrayList<>();
        employeeResult.add(employee);
        given(employeeService.getAll()).willReturn(employeeResult);

        mockMvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name").value("Hello"))
                .andExpect(jsonPath("$[0].surname").value("World"));
    }

    @Test
    public void shouldTestGetEmployeesByIdNoResult() throws Exception
    {
        given(employeeService.getById(anyInt())).willReturn(java.util.Optional.empty());

        mockMvc.perform(get("/employees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldAddValidEmployee() throws Exception
    {
        given(employeeService.addEmployee(employee)).willReturn(employee);

        mockMvc.perform(post("/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJsonRequest(employee))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotAddEmployee() throws Exception
    {
        mockMvc.perform(post("/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJsonRequest(new Employee()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteEmployee() throws Exception
    {
        mockMvc.perform(delete("/employees/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotDeleteAnyEmployeeWhenNoMatchingEmployee() throws Exception
    {
        doThrow(IllegalArgumentException.class).when(employeeService).deleteEmployee(anyInt());

        mockMvc.perform(delete("/employees/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldUpdateEmployee() throws Exception
    {
        given(employeeService.updateEmployee(1, employee)).willReturn(employee);

        mockMvc.perform(put("/employees/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJsonRequest(employee))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    private String getJsonRequest(Employee employee) throws JsonProcessingException
    {
        return objectMapper.writeValueAsString(employee);
    }
}
