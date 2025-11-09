package com.employee.servlet;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registerEmployee")
public class EmployeeServlet extends HttpServlet {
    
    private EmployeeDAO employeeDAO;
    
    @Override
    public void init() {
        employeeDAO = new EmployeeDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String empId = request.getParameter("empId");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String department = request.getParameter("department");
        String designation = request.getParameter("designation");
        String joiningDateStr = request.getParameter("joiningDate");
        String salaryStr = request.getParameter("salary");
        
        try {
            Employee employee = new Employee();
            employee.setEmpId(empId);
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setEmail(email);
            employee.setPhone(phone);
            employee.setDepartment(department);
            employee.setDesignation(designation);
            employee.setJoiningDate(Date.valueOf(joiningDateStr));
            employee.setSalary(Double.parseDouble(salaryStr));
            
            if (employeeDAO.employeeExists(empId)) {
                showErrorPage(out, "Employee ID already exists!", empId);
                return;
            }
            
            boolean isInserted = employeeDAO.insertEmployee(employee);
            
            if (isInserted) {
                showSuccessPage(out, employee);
            } else {
                showErrorPage(out, "Failed to register employee. Please try again.", empId);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            showErrorPage(out, "Error: " + e.getMessage(), empId);
        }
    }
    
    private void showSuccessPage(PrintWriter out, Employee employee) {
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Success</title>");
        out.println("<style>body{font-family:Arial;background:linear-gradient(135deg,#667eea,#764ba2);padding:20px;}");
        out.println(".container{max-width:600px;margin:50px auto;background:white;padding:30px;border-radius:10px;box-shadow:0 10px 25px rgba(0,0,0,0.2);}");
        out.println("h2{color:#667eea;text-align:center;}.success-msg{background:#d4edda;color:#155724;padding:15px;border-radius:5px;margin-bottom:20px;text-align:center;}");
        out.println("table{width:100%;border-collapse:collapse;margin-top:20px;}td{padding:12px;border-bottom:1px solid #ddd;}");
        out.println("td:first-child{font-weight:bold;color:#667eea;width:40%;}.btn{display:inline-block;margin-top:20px;padding:10px 20px;background:#667eea;color:white;text-decoration:none;border-radius:5px;}");
        out.println("</style></head><body><div class='container'>");
        out.println("<h2>Employee Registration Successful!</h2>");
        out.println("<div class='success-msg'>✓ Employee saved to AWS RDS successfully!</div>");
        out.println("<table>");
        out.println("<tr><td>Employee ID:</td><td>" + employee.getEmpId() + "</td></tr>");
        out.println("<tr><td>Full Name:</td><td>" + employee.getFullName() + "</td></tr>");
        out.println("<tr><td>Email:</td><td>" + employee.getEmail() + "</td></tr>");
        out.println("<tr><td>Phone:</td><td>" + employee.getPhone() + "</td></tr>");
        out.println("<tr><td>Department:</td><td>" + employee.getDepartment() + "</td></tr>");
        out.println("<tr><td>Designation:</td><td>" + employee.getDesignation() + "</td></tr>");
        out.println("<tr><td>Joining Date:</td><td>" + employee.getJoiningDate() + "</td></tr>");
        out.println("<tr><td>Salary:</td><td>₹ " + employee.getSalary() + "</td></tr>");
        out.println("</table>");
        out.println("<center><a href='index.html' class='btn'>Register Another</a> ");
        out.println("<a href='listEmployees' class='btn' style='background:#28a745;'>View All</a></center>");
        out.println("</div></body></html>");
    }
    
    private void showErrorPage(PrintWriter out, String errorMsg, String empId) {
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Error</title>");
        out.println("<style>body{font-family:Arial;background:linear-gradient(135deg,#667eea,#764ba2);padding:20px;}");
        out.println(".container{max-width:600px;margin:50px auto;background:white;padding:30px;border-radius:10px;}");
        out.println("h2{color:#e74c3c;text-align:center;}.error-msg{background:#f8d7da;color:#721c24;padding:15px;border-radius:5px;text-align:center;}");
        out.println(".btn{display:inline-block;margin-top:20px;padding:10px 20px;background:#667eea;color:white;text-decoration:none;border-radius:5px;}");
        out.println("</style></head><body><div class='container'>");
        out.println("<h2>Registration Failed</h2>");
        out.println("<div class='error-msg'>✗ " + errorMsg + "</div>");
        out.println("<p style='text-align:center;'>Employee ID: <strong>" + empId + "</strong></p>");
        out.println("<center><a href='index.html' class='btn'>Try Again</a></center>");
        out.println("</div></body></html>");
    }
}
