package DataAcessObjects;

import Database.DatabaseConnection;
import Model.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Employee_DAO {


    public boolean insertEmployee(Employee employee) throws SQLException {

        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        if (connection.getAutoCommit())     //commit only after making sure changes are made
            connection.setAutoCommit(false);

        //insert employee query
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into employees(UserID,FirstName,LastName,Role,Age,Salary,Phone)\n" +
                "values(?,?,?,?,?,?,?)")) {
            preparedStatement.setInt(1,employee.getUserId());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getRole());
            preparedStatement.setInt(5, employee.getAge());
            preparedStatement.setFloat(6, employee.getSalary());
            preparedStatement.setString(7, employee.getPhone());

            preparedStatement.executeUpdate();      //execute query and commit
            connection.commit();
            preparedStatement.close();

            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            connection.rollback();

            return false;
        }
    }


}
