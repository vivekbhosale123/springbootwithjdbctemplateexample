package com.vdb.controller;

import com.vdb.exception.RecordNotFoundException;
import com.vdb.model.Employee;
import com.vdb.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Employee employee)
    {
        employeeService.signUp(employee);

        return ResponseEntity.ok("employee added Successfully");
    }

    @PostMapping("/savebulkdata")
    public ResponseEntity<String> saveAll(@RequestBody List<Employee> employeeList)
    {
        employeeService.saveAll(employeeList);

        return ResponseEntity.ok("all employee added successfully");
    }

    @GetMapping("/signin/{empEmailId}/{empPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String empEmailId,@PathVariable String empPassword)
    {
        return ResponseEntity.ok(employeeService.signIn(empEmailId, empPassword));
    }

    @GetMapping("/findbyid/{empId}")
    public ResponseEntity<Optional<Employee>> findById(@PathVariable int empId)
    {
        return ResponseEntity.ok(employeeService.findById(empId));
    }

    @GetMapping("/findbyanyinput/{input}")
    public ResponseEntity<List<Employee>> findByAnyInput(@PathVariable String input)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-mm-yyyy");

        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp->emp.getEmpName().equals(input)
        ||String.valueOf(emp.getEmpId()).equals(input) ||String.valueOf(emp.getEmpContactNumber()).equals(input)
                ||simpleDateFormat.format(emp.getEmpDob()).equals(input)
                || emp.getEmpEmailId().equals(input)
        ).toList());
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Employee>> findAll()
    {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/findbyname/{empName}")
    public ResponseEntity<List<Employee>> findByName(@PathVariable String empName)
    {
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp->emp.getEmpName().equals(empName)).toList());
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Employee>> sortByName()
    {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpName)).toList());
    }

    @GetMapping("/sortbysalary")
    public ResponseEntity<List<Employee>> sortBySalary()
    {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpSalary)).toList());
    }

    @GetMapping("/sortbydob")
    public ResponseEntity<List<Employee>> sortByDob()
    {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpDob)).toList());
    }

    @PutMapping("/update/{empId}")
    public ResponseEntity<String> update(@PathVariable int empId,@RequestBody Employee employee)
    {
        Employee employee1=employeeService.findById(empId).orElseThrow(()->new RecordNotFoundException("Employee Id Does not Exists"));

        employee1.setEmpName(employee.getEmpName());
        employee1.setEmpAddress(employee.getEmpAddress());
        employee1.setEmpSalary(employee.getEmpSalary());
        employee1.setEmpContactNumber(employee.getEmpContactNumber());
        employee1.setEmpDob(employee.getEmpDob());
        employee1.setEmpEmailId(employee.getEmpEmailId());
        employee1.setEmpPassword(employee.getEmpPassword());

        employeeService.update(empId,employee);

        return ResponseEntity.ok("Updated Successfully");

    }

    @DeleteMapping("/delete/{empId}")
    public ResponseEntity<String> delete(@PathVariable int empId)
    {
        employeeService.deleteById(empId);
        return ResponseEntity.ok("employee deleted successfully");
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll()
    {
        employeeService.deleteAll();

        return ResponseEntity.ok("all employee deleted successfully");
    }

}
