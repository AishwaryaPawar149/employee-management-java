package com.employee.dao;

import com.employee.model.Employee;
import com.employee.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    
    public boolean insertEmployee(Employee employee) {
        String sql = "INSERT INTO employees (emp_id, first_name, last_name, email, phone, " +
                    "department, designation, joining_date, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getEmpId());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setString(3, employee.getLastName());
            pstmt.setString(4, employee.getEmail());
            pstmt.setString(5, employee.getPhone());
            pstmt.setString(6, employee.getDepartment());
            pstmt.setString(7, employee.getDesignation());
            pstmt.setDate(8, employee.getJoiningDate());
            pstmt.setDouble(9, employee.getSalary());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error inserting employee: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY created_at DESC";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmpId(rs.getString("emp_id"));
                emp.setFirstName(rs.getString("first_name"));
                emp.setLastName(rs.getString("last_name"));
                emp.setEmail(rs.getString("email"));
                emp.setPhone(rs.getString("phone"));
                emp.setDepartment(rs.getString("department"));
                emp.setDesignation(rs.getString("designation"));
                emp.setJoiningDate(rs.getDate("joining_date"));
                emp.setSalary(rs.getDouble("salary"));
                employees.add(emp);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching employees: " + e.getMessage());
            e.printStackTrace();
        }
        
        return employees;
    }
    
    public boolean employeeExists(String empId) {
        String sql = "SELECT COUNT(*) FROM employees WHERE emp_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, empId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking employee existence: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public int getTotalEmployeeCount() {
        String sql = "SELECT COUNT(*) FROM employees";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error counting employees: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
}
