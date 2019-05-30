package com.example.api.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@Entity
@Table(
        name = "EMPLOYEE",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"})}
)
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    private String surname;

    @Min(1)
    @Max(10)
    private Integer grade;

    @PositiveOrZero
    private Integer salary;

    public Employee()
    {
    }

    public Employee(String name, String surname, int grade, int salary)
    {
        this.name = name;
        this.surname = surname;
        this.grade = grade;
        this.salary = salary;
    }
}
