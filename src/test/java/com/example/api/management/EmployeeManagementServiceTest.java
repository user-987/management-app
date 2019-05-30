package com.example.api.management;

import com.example.api.model.Employee;
import com.example.api.model.EmployeeRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeManagementServiceTest
{
    @MockBean
    private EmployeeRepository employeeRepository;

    private EmployeeManagementService employeeManagementService;

    private Employee employee;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception
    {
        employeeManagementService = new EmployeeManagementService(employeeRepository);
        employee = new Employee("nameTest", "surnameTest", 1, 1);
    }

    @Test
    public void shouldGetEmptyResultWhenNoEmployees()
    {
        // given
        given(employeeRepository.findAll()).willReturn(emptyList());

        // when
        List<Employee> all = employeeManagementService.getAll();

        // then
        assertTrue(all.isEmpty());
    }

    @Test
    public void shouldGetAllEmployees()
    {
        // given
        given(employeeRepository.findAll()).willReturn(singletonList(employee));

        // when
        List<Employee> all = employeeManagementService.getAll();

        // then
        assertEquals(all.size(), 1);
    }

    @Test
    public void shouldAddValidEmployee()
    {
        // given
        given(employeeRepository.save(employee)).willReturn(employee);

        // when
        Employee employeeAdded = employeeManagementService.addEmployee(employee);

        // then
        assertTrue(employeeAdded.equals(employee));
    }

    @Test
    public void shouldNotAddNotValidEmployee()
    {
        // given
        given(employeeRepository.save(employee)).willThrow(IllegalArgumentException.class);

        // when
        exception.expect(IllegalArgumentException.class);
        employeeManagementService.addEmployee(employee);
    }

    @Test
    public void shouldNotDeleteWhenNoMatchingEmployee()
    {
        // given
        given(employeeRepository.findById(1)).willReturn(Optional.empty());

        // when
        exception.expect(IllegalArgumentException.class);
        employeeManagementService.deleteEmployee(1);
    }

    @Test
    public void shouldUpdateEmployee()
    {
        // given
        given(employeeRepository.findById(1)).willReturn(Optional.ofNullable(employee));

        // when
        employeeManagementService.updateEmployee(1, employee);

        // then
    }

    @Test
    public void shouldAddEmployeeWhenNoMatchingEmployeeToUpdate()
    {
        // given
        given(employeeRepository.findById(1)).willReturn(Optional.empty());

        // when
        Employee employeeAdded = employeeManagementService.updateEmployee(1, this.employee);

        // then
        assertTrue(employeeAdded.equals(employee));
    }
}
