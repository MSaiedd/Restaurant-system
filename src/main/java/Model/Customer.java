package Model;

import DataAcessObjects.Customer_DAO;
import java.sql.SQLException;

public class Customer {
    public String FirstName;
    public String LastName;
    public String Phone;
    public int UserID;

    public int getUserID() {
        return UserID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setFirstName(String firstName) {FirstName = firstName;}

    public void setLastName(String lastName) {LastName = lastName;}

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setUserID(int userID) {UserID = userID;}

    public Customer(String firstName, String lastName, String phone, int userid) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Phone = phone;
        this.UserID=userid;
    }
    public Customer(){}
    public boolean addCustomer(Customer customer) throws SQLException {
        Customer_DAO customerDao = new Customer_DAO();
        if (customerDao.insertCustomer(customer))
            return true;
        return false;
    }

    public int getCustomerIdByUserId(int id) {
        Customer_DAO customerDao=new Customer_DAO();
        return customerDao.fetchCusomerIdByUserId(id);
    }
}
