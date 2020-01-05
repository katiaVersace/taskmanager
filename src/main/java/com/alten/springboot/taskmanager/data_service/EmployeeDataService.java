package com.alten.springboot.taskmanager.data_service;



import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.alten.springboot.taskmanager.entity.Employee;

public interface EmployeeDataService extends UserDetailsService 
{

    Employee findByUserName(String userName);
    
    List<Employee> findAll();

    Employee findById(int employeeId);
    
    Employee save(Employee employee);
    
    Employee update(Employee employee);
    
    void delete(int employeeId);
    
  
   
    
   

	
 
}
