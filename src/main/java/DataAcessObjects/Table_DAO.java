package DataAcessObjects;

import Database.DatabaseConnection;
import Model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Table_DAO {

    public int getTableID(Table table){

        //get instance with sql database
        DatabaseConnection databaseConnection=DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //get table id query
        try (PreparedStatement preparedStatement = connection.prepareStatement("select *  from `table` where area=? and max_seats=? and status='not-reserved'")) {
            System.out.println(table.getArea().toLowerCase().strip());
            System.out.println(table.getMax_seats());
            preparedStatement.setString(1, table.getArea().toLowerCase().strip());
            preparedStatement.setInt(2,table.getMax_seats());
            ResultSet resultSet=preparedStatement.executeQuery();  // excute query

            //return id if its valid
            if (resultSet.next())
                return resultSet.getInt(1);
            else
                return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int reserveTable(Table table){

        //check if table is not found
        int id = getTableID(table);
        if (id==0)
            return 0;

        //get instance with sql database
        DatabaseConnection databaseConnection=DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //reserve table query
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `table` SET status = 'reserved' WHERE Table_ID=?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();      // excute query and return
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public boolean removeReservedStatus(int id){

        //get instance with sql database
        DatabaseConnection databaseConnection=DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //cancel table reservation status query
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `table` SET status = 'not-reserved' WHERE Table_ID=?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate(); //excute and return
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
