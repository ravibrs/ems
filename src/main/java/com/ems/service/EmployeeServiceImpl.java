package com.ems.service;

import com.ems.exception.EmployeeNotFoundException;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees");
        return repository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        log.info("Creating new employee: {}", employee);
        return repository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        log.info("Updating employee with ID: {}", id);
        Employee existing = getEmployeeById(id);
        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setEmailAddress(employee.getEmailAddress());
        existing.setPhone(employee.getPhone());
        existing.setBirthDate(employee.getBirthDate());
        existing.setJobTitle(employee.getJobTitle());
        existing.setDepartment(employee.getDepartment());
        existing.setLocation(employee.getLocation());
        existing.setStartDate(employee.getStartDate());
        existing.setManagerReporting(employee.getManagerReporting());

        return repository.save(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        Employee employee = getEmployeeById(id);
        repository.delete(employee);
    }
}