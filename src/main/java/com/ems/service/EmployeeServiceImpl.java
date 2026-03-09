package com.ems.service;

import com.ems.exception.EmployeeNotFoundException;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {

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

        Employee employee = getEmployeeById(id);
        repository.delete(employee);
    }
}