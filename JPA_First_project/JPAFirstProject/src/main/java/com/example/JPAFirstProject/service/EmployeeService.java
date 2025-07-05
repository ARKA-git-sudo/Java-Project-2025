package com.example.JPAFirstProject.service;


import com.example.JPAFirstProject.entity.Employee;
import com.example.JPAFirstProject.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    public List<Employee> getAllEmp(){
        List<Employee> employeeList = employeeRepo.findAll();
        return employeeList;
    }

    public Employee CreateEmployee(Employee employee) {
        Employee save = employeeRepo.save(employee);
        return save;
    }

    public void DeleteEmpById(int id) {
        employeeRepo.deleteById(id);
    }

    public boolean ExistById(int id) {
        return employeeRepo.existsById(id);
    }

    public Optional<Employee> findById(int id) {
        return employeeRepo.findById(id);
    }



}
