package com.vdb.dao;

import com.vdb.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeDaoImpl implements IEmployeeDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Employee employee(ResultSet rs, int rowNum) throws SQLException {
        return Employee.builder()
                .empId(rs.getInt(1))
                .empName(rs.getString(2))
                .empAddress(rs.getString(3))
                .empSalary(rs.getDouble(4))
                .empContactNumber(rs.getLong(5))
                .empDob(rs.getDate(6))
                .empEmailId(rs.getString(7))
                .empPassword(rs.getString(8))
                .build();
    }

    String insertSQL="insert into employee(empid,empname,empaddress,empsalary,empcontactnumber,empdob,empemaildid,emppassword)values(?,?,?,?,?,?,?,?)";
    String selectByIdSQL="select * from employee where empid=?";
    String selectAllSQL="select * from employee";
    String updateSQL="update employee set empname=?,empaddress=?,empsalary=?,empcontactnumber=?,empdob=?,empemaildid=?,emppassword=? where empid=?";
    String deleteByIdSQL="delete from employee where empid=?";
    String deleteAllSQL="truncate table employee";

    @Override
    public void signUp(Employee employee) {
        jdbcTemplate.update(insertSQL,employee.getEmpId(),employee.getEmpName(),employee.getEmpAddress(),employee.getEmpSalary(),employee.getEmpContactNumber(),employee.getEmpDob(),employee.getEmpEmailId(),employee.getEmpPassword());
    }

    @Override
    public boolean signIn(String empEmailId, String empPassword) {

        boolean flag=false;

        for(Employee employee:findAll())
        {
            if(employee.getEmpEmailId().equals(empEmailId) && employee.getEmpPassword().equals(empPassword))
            {
                flag=true;
                break;
            }
        }
        return flag;
    }

    @Override
    public void saveAll(List<Employee> employees) {

        for (Employee employee:employees)
        {
            jdbcTemplate.update(insertSQL,employee.getEmpId(),employee.getEmpName(),employee.getEmpAddress(),employee.getEmpSalary(),employee.getEmpContactNumber(),employee.getEmpDob(),employee.getEmpEmailId(),employee.getEmpPassword());
        }
    }

    @Override
    public Optional<Employee> findById(int empId) {

        List<Employee> employees=jdbcTemplate.query(selectByIdSQL,this::employee,empId);
        if(employees.isEmpty())
        {
            return Optional.empty();
        }else{
            return Optional.of(employees.get(0));
        }
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query(selectAllSQL,this::employee);
    }

    @Override
    public void update(int empId, Employee employee) {
       jdbcTemplate.update(updateSQL,employee.getEmpName(),employee.getEmpAddress(),employee.getEmpSalary(),employee.getEmpContactNumber(),employee.getEmpDob(),employee.getEmpEmailId(),employee.getEmpPassword(),empId);
    }

    @Override
    public void deleteById(int empId) {
       jdbcTemplate.update(deleteByIdSQL,empId);
    }

    @Override
    public void deleteAll() {
         jdbcTemplate.update(deleteAllSQL);
    }
}
