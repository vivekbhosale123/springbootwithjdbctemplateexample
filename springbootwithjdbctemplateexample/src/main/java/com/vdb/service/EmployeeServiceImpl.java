package com.vdb.service;

import com.vdb.dao.EmployeeDaoImpl;
import com.vdb.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService{

    @Autowired
    private EmployeeDaoImpl employeeDao;

    @Override
    public void signUp(Employee employee) {
       employeeDao.signUp(employee);
    }

    @Override
    public boolean signIn(String empEmailId, String empPassword) {
        return employeeDao.signIn(empEmailId, empPassword);
    }

    @Override
    public void saveAll(List<Employee> employees) {
        employeeDao.saveAll(employees);
    }

    @Override
    public Optional<Employee> findById(int empId) {
        return employeeDao.findById(empId);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public void update(int empId, Employee employee) {
        employeeDao.update(empId, employee);
    }

    @Override
    public void deleteById(int empId) {
       employeeDao.deleteById(empId);
    }

    @Override
    public void deleteAll() {
      employeeDao.deleteAll();
    }
}
