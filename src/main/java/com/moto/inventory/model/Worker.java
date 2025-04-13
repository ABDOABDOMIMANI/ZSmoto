package com.moto.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Entity
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Salary is required")
    @PositiveOrZero(message = "Salary must be positive or zero")
    private BigDecimal salary;

    @NotNull(message = "Worker role is required")
    @Enumerated(EnumType.STRING)
    private WorkerRole workerRole;

    // Constructors
    public Worker() {
    }

    public Worker(String name, BigDecimal salary, WorkerRole workerRole) {
        this.name = name;
        this.salary = salary;
        this.workerRole = workerRole;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public WorkerRole getWorkerRole() {
        return workerRole;
    }

    public void setWorkerRole(WorkerRole workerRole) {
        this.workerRole = workerRole;
    }
}