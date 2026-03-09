package com.ems.service;

import com.ems.exception.EmployeeNotFoundException;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setup() {
        employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setFirstName("Ravi");
        employee.setLastName("Boddupalli");
        employee.setEmailAddress("ravi.boddupalli@test.com");
        employee.setPhone("9999999999");
        employee.setBirthDate(LocalDate.of(1990,5,10));
        employee.setJobTitle("Engineer");
        employee.setDepartment("IT");
        employee.setLocation("Virginia");
        employee.setStartDate(LocalDate.of(2023,1,1));
        employee.setManagerReporting("Scott");
    }

    @Test
    void getAllEmployeesTest() {
        List<Employee> list = Arrays.asList(employee);
        when(repository.findAll()).thenReturn(list);
        List<Employee> result = employeeService.getAllEmployees();
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getEmployeeByIdTest() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        Employee result = employeeService.getEmployeeById(1L);
        assertEquals("Ravi", result.getFirstName());
        verify(repository).findById(1L);
    }

    @Test
    void getEmployeeById_NotFoundTest() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployeeById(1L);
        });
    }

    @Test
    void createEmployeeTest() {
        when(repository.save(employee)).thenReturn(employee);
        Employee saved = employeeService.createEmployee(employee);
        assertNotNull(saved);
        assertEquals("Ravi", saved.getFirstName());
        verify(repository).save(employee);
    }

    @Test
    void updateEmployeeTest() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        when(repository.save(employee)).thenReturn(employee);
        Employee updated = employeeService.updateEmployee(1L, employee);
        assertEquals("Ravi", updated.getFirstName());
        verify(repository).save(employee);
    }

    @Test
    void deleteEmployeeTest() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        employeeService.deleteEmployee(1L);
        verify(repository).delete(employee);
    }
}
