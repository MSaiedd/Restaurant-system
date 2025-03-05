package DataAcessObjects;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuItem_DAO {

    public ArrayList<String> getMenuItemsByType(String type) {

        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //get menu-items by item type query
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from menuitem where type=?")) {
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();

            //save menu-items in list
            ArrayList<String> list=new ArrayList<>();
            while (resultSet.next()){
                list.add(resultSet.getString(4));
                list.add(String.valueOf(resultSet.getInt(3)));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getMenuIdByName(String name) {

        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //get menu item id using name query
        try (PreparedStatement preparedStatement = connection.prepareStatement("select MenuItem_ID from menuitem where name=?")) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            // return menu-item id
            if (resultSet.next())
                return resultSet.getInt(1);

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public float getMenuPriceByName(String name) {

        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //get menu item price by its name query
        try (PreparedStatement preparedStatement = connection.prepareStatement("select price from menuitem where name=?")) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            //return result
            if (resultSet.next())
                return resultSet.getInt(1);

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


}
