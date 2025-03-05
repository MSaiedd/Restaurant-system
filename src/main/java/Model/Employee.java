package Model;

import DataAcessObjects.Employee_DAO;
import java.sql.SQLException;

public class Employee {
    private int employeeId;
    private int userId;
    private String firstName;
    private String lastName;
    private String role;
    private int age;
    private float salary;
    private String phone;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Employee(int userId, String firstName, String lastName, String role, int age, float salary, String phone) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.age = age;
        this.salary = salary;
        this.phone = phone;
    }

    public boolean addEmployee(Employee employee) throws SQLException {
        Employee_DAO employeeDao = new Employee_DAO();
        if (employeeDao.insertEmployee(employee))
            return true;
        return false;
    }
}
