package com.example.api.search;

import com.example.api.model.Employee;
import com.example.api.model.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class EmployeeSearchService
{
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeSearchService(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> search(Employee employee)
    {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(employee, matcher);
        try
        {
            return employeeRepository.findAll(example);
        } catch (DataIntegrityViolationException e)
        {
            log.error("Incorrect employee search data", e);
            throw new IllegalArgumentException("Incorrect employee search data: data integrity violation");
        }
    }
}
