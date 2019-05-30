package com.example.api.search;

import com.example.api.model.Employee;
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
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeSearchControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeSearchService employeeService;

    @Test
    public void shouldTestEmployeeResultNotFound() throws Exception
    {
        given(employeeService.search(any())).willReturn(emptyList());

        mockMvc.perform(get("/employees/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(anyString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldTestEmployeeResultFound() throws Exception
    {
        List<Employee> employeeResult = new ArrayList<>();
        employeeResult.add(new Employee());
        given(employeeService.search(any())).willReturn(employeeResult);

        mockMvc.perform(get("/employees/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(anyString()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldTestIllegalArgumentException() throws Exception
    {
        given(employeeService.search(any())).willThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/employees/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(anyString()))
                .andExpect(status().is4xxClientError());
    }
}
