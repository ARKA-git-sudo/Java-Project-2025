package com.example.JPAFirstProject.collection;


import com.example.JPAFirstProject.entity.Employee;
import com.example.JPAFirstProject.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @GetMapping("/getAll")
    public List<Employee> getAllEmployee(){

        List<Employee> allEmp = employeeService.getAllEmp();
        return allEmp;
    }

    @PostMapping("/save")
    public Employee CreateEmployee(@RequestBody Employee employee){
        Employee createEmployee = employeeService.CreateEmployee(employee);
        return createEmployee;
    }


    @DeleteMapping("/delete/{id}")
    public String DeleteEmpById(@PathVariable int id){
        employeeService.DeleteEmpById(id);
        return "Employee with ID " + id + " deleted successfully.";
    }

    @GetMapping("/exist/{id}")
    public String ExistById(@PathVariable int id){
        boolean existById = employeeService.ExistById(id);
        if(existById){
            return "Employee with Id "+id+ " is present in Database";
        }else{
            return "Employee with Id "+id+ " is not present in Database";
        }
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return ResponseEntity.status(404).body("Employee with ID " + id + " not found.");
        }
    }


}
