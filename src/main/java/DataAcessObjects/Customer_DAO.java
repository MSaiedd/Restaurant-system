package DataAcessObjects;

import Database.DatabaseConnection;
import Model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer_DAO {

    public boolean insertCustomer(Customer customer) throws SQLException {
        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        if (connection.getAutoCommit())         //commit only after making sure changes are made
            connection.setAutoCommit(false);

        //insert customer to database
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customers (FirstName, LastName,Phone,UserID) VALUES (?, ?,?,?)")) {
            preparedStatement.setString(1,customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setInt(4, customer.getUserID());
            preparedStatement.executeUpdate();
            connection.commit();        //commit
            preparedStatement.close();  //close resources
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        }
    }

    public int fetchCusomerIdByUserId(int id) {
        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //get customer id query
        try (PreparedStatement preparedStatement = connection.prepareStatement("select CustomerID from customers where UserID=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            // return customer id if It's valid if not return -1
            if (resultSet.next())
                return resultSet.getInt(1);
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
