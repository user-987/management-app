package com.example.api.management;

import com.example.api.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeManagementController
{
    private final EmployeeManagementService employeeService;

    @Autowired
    public EmployeeManagementController(EmployeeManagementService employeeService)
    {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees()
    {
        return employeeService.getAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id)
    {
        Optional<Employee> employee = employeeService.getById(id);
        if (employee.isPresent())
        {
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/employees/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@Valid @RequestBody Employee employee, BindingResult result)
    {
        if (result.hasErrors())
        {
            throw new IllegalArgumentException("Please enter valid employee data");
        }
        return employeeService.addEmployee(employee);
    }

    @DeleteMapping("/employees/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") int id)
    {
        employeeService.deleteEmployee(id);
    }

    @PutMapping("employees/update/{id}")
    public Employee updateEmployee(@PathVariable("id") int id, @Valid @RequestBody Employee employee, BindingResult result)
    {
        if (result.hasErrors())
        {
            throw new IllegalArgumentException("Please enter valid employee data to be updated");
        }
        return employeeService.updateEmployee(id, employee);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity handleException(IllegalArgumentException e)
    {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
