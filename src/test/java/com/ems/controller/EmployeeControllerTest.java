package com.ems.controller;
import com.ems.model.Employee;
import com.ems.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEmployeesTest() throws Exception {
        Employee emp = new Employee();
        emp.setEmployeeId(1L);
        emp.setFirstName("Ravi");
        List<Employee> employees = Arrays.asList(emp);
        when(service.getAllEmployees()).thenReturn(employees);
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void getEmployeeByIdTest() throws Exception {
        Employee emp = new Employee();
        emp.setEmployeeId(1L);
        emp.setFirstName("Ravi");
        when(service.getEmployeeById(1L)).thenReturn(emp);
        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ravi"));
    }

    @Test
    void createEmployeeTest() throws Exception {
        Employee emp = new Employee();
        emp.setEmployeeId(1L);
        emp.setFirstName("Ravi");
        when(service.createEmployee(emp)).thenReturn(emp);
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ravi"));
    }

    @Test
    void updateEmployeeTest() throws Exception {
        Employee emp = new Employee();
        emp.setEmployeeId(1L);
        emp.setFirstName("Updated");
        when(service.updateEmployee(1L, emp)).thenReturn(emp);
        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));
    }

    @Test
    void deleteEmployeeTest() throws Exception {
        doNothing().when(service).deleteEmployee(1L);
        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isOk());
    }
}