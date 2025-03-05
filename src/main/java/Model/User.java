package Model;

import DataAcessObjects.User_DAO;
import java.sql.SQLException;

public class User {

    private String Email;
    private String Password;

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public User(CharSequence email, CharSequence password) {
        this.Email = email.toString();
        this.Password = password.toString();
       }

    public boolean addUser(User user,String firstname ,String lastname,String phone) throws SQLException {
        User_DAO userDao = new User_DAO();
        if (userDao.insertUser(user) )      //check if user added user to database
        {
            //then add customer
            Customer customer=new Customer(firstname,lastname,phone,userDao.fetchUserIdByEmailAndPass(null));
            return customer.addCustomer(customer);
        }
        return false;
    }

    public boolean addUser(User user,String firstname ,String lastname,String role,String age ,String salary,String phone) throws SQLException {
        User_DAO userDao = new User_DAO();
        if (userDao.insertUser(user)) {    //check if user added user to database
            //then add employee
            Employee employee=new Employee(userDao.fetchUserIdByEmailAndPass(null),firstname,lastname,role,Integer.parseInt(age),Float.parseFloat(salary),phone);
            return employee.addEmployee(employee);
        }
        return false;
    }


    public int getUserType(User user) {
        User_DAO userDao = new User_DAO();
        System.out.println("KKKKKKKKKKKKKKKKKKKK");
        System.out.println(userDao.fetchRole(user));
        System.out.println("KKKKKKKKKKKKKKKKKKKK");
        return userDao.fetchRole(user);
    }

    public int getUserID(User user) throws SQLException {
        User_DAO userDao = new User_DAO();
        return userDao.fetchUserIdByEmailAndPass(user);
    }




}
