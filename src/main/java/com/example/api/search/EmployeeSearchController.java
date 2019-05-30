package com.example.api.search;

import com.example.api.model.Employee;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
public class EmployeeSearchController
{
    private final EmployeeSearchService employeeService;

    @Autowired
    public EmployeeSearchController(EmployeeSearchService employeeSearchService)
    {
        this.employeeService = employeeSearchService;
    }

    @GetMapping("/employees/search")
    public ResponseEntity<List<Employee>> getAllEmployees(Employee employee)
    {
        List<Employee> result = employeeService.search(employee);
        if (result.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity handleException(IllegalArgumentException e)
    {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
