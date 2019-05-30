package com.example.api.management;

import com.example.api.model.Employee;
import com.example.api.model.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class EmployeeManagementService
{
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeManagementService(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll()
    {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getById(int id)
    {
        return employeeRepository.findById(id);
    }

    public Employee addEmployee(Employee employee)
    {
        try
        {
            employeeRepository.save(employee);
        } catch (DataIntegrityViolationException e)
        {
            log.error("Error when adding an employee", e);
            throw new IllegalArgumentException("Error occurred when adding an employee: data integrity violation");
        }
        log.info("Employee added");
        return employee;
    }

    public void deleteEmployee(int id)
    {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee with id=" + id + " not found"));
        employeeRepository.delete(employee);
        log.info("Employee with id={} deleted", id);
    }

    public Employee updateEmployee(int id, Employee employee)
    {
        Optional<Employee> employeeOld = employeeRepository.findById(id);
        Employee employeeUpdated;

        if (employeeOld.isPresent())
        {
            Employee employeeNew = employeeOld.get();
            employeeNew.setName(employee.getName());
            employeeNew.setSurname(employee.getSurname());
            employeeNew.setGrade(employee.getGrade());
            employeeNew.setSalary(employee.getSalary());
            employeeUpdated = addEmployee(employeeNew);
        } else
        {
            employeeUpdated = addEmployee(employee);
        }
        log.info("Employee with id={} updated", id);
        return employeeUpdated;
    }
}
