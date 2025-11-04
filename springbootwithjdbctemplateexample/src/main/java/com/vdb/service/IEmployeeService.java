package com.vdb.service;

import com.vdb.model.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    void signUp(Employee employee);

    boolean signIn(String empEmailId,String empPassword);

    void saveAll(List<Employee> employees);

    Optional<Employee> findById(int empId);

    List<Employee> findAll();

    void update(int empId,Employee employee);

    void deleteById(int empId);

    void deleteAll();
}
