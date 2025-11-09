package com.employee.servlet;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/listEmployees")
public class ListEmployeeServlet extends HttpServlet {
    
    private EmployeeDAO employeeDAO;
    
    @Override
    public void init() {
        employeeDAO = new EmployeeDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        List<Employee> employees = employeeDAO.getAllEmployees();
        int totalCount = employeeDAO.getTotalEmployeeCount();
        
        out.println("<!DOCTYPE html><html><head><title>Employee List</title>");
        out.println("<style>*{margin:0;padding:0;box-sizing:border-box;}");
        out.println("body{font-family:'Segoe UI',sans-serif;background:linear-gradient(135deg,#667eea,#764ba2);padding:20px;}");
        out.println(".container{max-width:1200px;margin:20px auto;background:white;padding:30px;border-radius:15px;}");
        out.println("h1{color:#667eea;text-align:center;margin-bottom:10px;}");
        out.println(".stats{text-align:center;color:#666;margin-bottom:30px;font-size:1.1em;}");
        out.println("table{width:100%;border-collapse:collapse;margin-top:20px;}");
        out.println("thead{background:linear-gradient(135deg,#667eea,#764ba2);color:white;}");
        out.println("th{padding:15px;text-align:left;font-weight:600;}");
        out.println("td{padding:12px 15px;border-bottom:1px solid #e0e0e0;}");
        out.println("tbody tr:hover{background:#f5f5f5;}");
        out.println(".btn{display:inline-block;margin:20px 5px;padding:12px 25px;background:#667eea;color:white;text-decoration:none;border-radius:8px;}");
        out.println("</style></head><body><div class='container'>");
        out.println("<h1>📊 Employee Directory</h1>");
        out.println("<div class='stats'>Total Employees: <strong>" + totalCount + "</strong></div>");
        out.println("<center><a href='index.html' class='btn'>➕ Add New Employee</a></center>");
        
        if (employees.isEmpty()) {
            out.println("<p style='text-align:center;padding:50px;color:#999;'>No employees found.</p>");
        } else {
            out.println("<table><thead><tr>");
            out.println("<th>Emp ID</th><th>Name</th><th>Email</th><th>Phone</th>");
            out.println("<th>Department</th><th>Designation</th><th>Joining Date</th><th>Salary</th>");
            out.println("</tr></thead><tbody>");
            
            for (Employee emp : employees) {
                out.println("<tr>");
                out.println("<td><strong>" + emp.getEmpId() + "</strong></td>");
                out.println("<td>" + emp.getFullName() + "</td>");
                out.println("<td>" + emp.getEmail() + "</td>");
                out.println("<td>" + emp.getPhone() + "</td>");
                out.println("<td>" + emp.getDepartment() + "</td>");
                out.println("<td>" + emp.getDesignation() + "</td>");
                out.println("<td>" + emp.getJoiningDate() + "</td>");
                out.println("<td>₹ " + String.format("%,.2f", emp.getSalary()) + "</td>");
                out.println("</tr>");
            }
            
            out.println("</tbody></table>");
        }
        
        out.println("<center><a href='index.html' class='btn'>← Back</a></center>");
        out.println("</div></body></html>");
    }
}
