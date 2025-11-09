package com.employee.model;

import java.sql.Date;

public class Employee {
    private String empId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private String designation;
    private Date joiningDate;
    private double salary;
    
    public Employee() {
    }
    
    public Employee(String empId, String firstName, String lastName, String email, 
                   String phone, String department, String designation, 
                   Date joiningDate, double salary) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.designation = designation;
        this.joiningDate = joiningDate;
        this.salary = salary;
    }
    
    public String getEmpId() {
        return empId;
    }
    
    public void setEmpId(String empId) {
        this.empId = empId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getDesignation() {
        return designation;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
    public Date getJoiningDate() {
        return joiningDate;
    }
    
    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", name='" + getFullName() + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
