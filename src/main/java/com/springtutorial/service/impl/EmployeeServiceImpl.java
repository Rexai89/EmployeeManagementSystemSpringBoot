package com.springtutorial.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.springtutorial.dto.EmployeeDto;
import com.springtutorial.mapper.EmployeeMapper;
import com.springtutorial.repository.EmployeeRepository;
import com.springtutorial.service.EmployeeService;
import com.springtutorial.entity.Employee;
import com.springtutorial.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    String employeeNotFoundMessage = "No employee with id: ";

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);      // maps the emplyeeDto to the employee
        Employee savedEmployee = employeeRepository.save(employee);         // saves the employee to be used later

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);              // maps the employee to the employeeDto and returns the emplyeeDto
    }
    
    @Override
    public EmployeeDto getEmployeeById(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() ->
                new ResourceNotFoundException(employeeNotFoundMessage+employeeId));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees(){

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map((employee) ->
            EmployeeMapper.mapToEmployeeDto(employee))
            .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() ->
                new ResourceNotFoundException(employeeNotFoundMessage+employeeId));

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() ->
                new ResourceNotFoundException(employeeNotFoundMessage+employeeId));        
        
        employeeRepository.deleteById(employeeId);
    }

}