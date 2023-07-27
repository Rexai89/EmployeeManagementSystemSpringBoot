package com.springtutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springtutorial.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long>{
}