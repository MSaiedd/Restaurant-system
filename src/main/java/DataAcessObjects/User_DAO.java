package DataAcessObjects;

import Database.DatabaseConnection;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class User_DAO {

    public int fetchRole(User user) {

        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //fetch role query
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT\s
                    CASE\s
                        WHEN e.userid IS NOT NULL THEN 'Employee'
                        WHEN c.userid IS NOT NULL THEN 'Customer'
                        ELSE 'Not Found'
                    END AS user_type,
                    e.role
                FROM user AS u
                LEFT JOIN employees AS e ON u.userid = e.userid
                LEFT JOIN customers AS c ON u.userid = c.userid
                WHERE u.userid = ?""")
        ) {

            int userid = fetchUserIdByEmailAndPass(user); //get userid
            System.out.println("userid"+userid);
            preparedStatement.setInt(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
                return -1;
            else if (Objects.equals(resultSet.getString(1), "Customer"))   //case if it's a customer
                return 0;
            else {                                                                        // employee
                switch (resultSet.getString(2)) {
                    case "chef" -> {
                        return 1;
                    }
                    case "waiter" -> {
                        return 2;
                    }
                    case "manager" -> {
                        return 3;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        return -1;
    }

    public int fetchUserIdByEmailAndPass(User user) throws SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();
        PreparedStatement preparedStatement;
        try {
            if (user==null)
                preparedStatement = connection.prepareStatement("select max(UserID)from user ");
            else {
                preparedStatement = connection.prepareStatement("select UserID from user where Email=? and Password= ?");
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getPassword());
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            int userid=-1;
            if (resultSet.next())
                userid = resultSet.getInt(1);
            System.out.println("user id  :" + userid);
            return userid;
        } catch (SQLException e) {
            connection.rollback();
            return -1;
        }finally {
            ///preparedStatement.close();
            ///connection.close();
        }

    }

    public boolean insertUser(User user) throws SQLException {

        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();
        connection.setAutoCommit(false);  //commit after all queries are done successfully

        //insert user query
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (email, password) VALUES (?,?)")) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();  //execute query
            connection.commit();                //commit
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        }
    }


}
