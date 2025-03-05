package DataAcessObjects;

import Database.DatabaseConnection;
import Model.Reservation;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Reservation_DAO {

    public boolean addRowToReservation(Reservation reservation) {

        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //insert reservation query
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into reservations(Table_ID,CustomerID,price) values(?,?,?)")) {
            preparedStatement.setInt(1, reservation.getTableId());
            preparedStatement.setInt(2, reservation.getCusomerId());

            //set reservation price if order is not made yet then price = null
            Float price = reservation.getPrice();  // Assuming getPrice() returns Float or null
            if (price != null)
                preparedStatement.setObject(3, price);
            else
                preparedStatement.setObject(3, null, java.sql.Types.FLOAT);

            preparedStatement.executeUpdate(); //excute query
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getReservationId(int customerid){

        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //get reservation id query
        try ( PreparedStatement preparedStatement = connection.prepareStatement("select Reservation_ID from reservations where CustomerID= ? order by date desc"))
        {

            preparedStatement.setInt(1, customerid);
            ResultSet resultSet = preparedStatement.executeQuery();

            //return result if valid
            if (!resultSet.next())
                return 0;
            else
               return resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean removeReservation(int id){

        //get instance with sql database
        DatabaseConnection databaseConnection=DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //remove reservation query
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from reservations where Reservation_ID=?")) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate(); // excute and return
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Reservation> fetchReservationsOfToday(){


        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //get some reservation details of today
        try(   PreparedStatement preparedStatement = connection.prepareStatement("SELECT Table_ID , FirstName,LastName\n" +
                "FROM reservations\n" +
                "INNER JOIN customers ON reservations.CustomerID = customers.CustomerID\n" +
                "WHERE DATE(reservations.date) = CURDATE()")) {

            ResultSet resultSet = preparedStatement.executeQuery();  //excute query

            //store rows in list of reservations then return it
            ArrayList<Reservation> reservations=new ArrayList<>();
            Reservation reservation=new Reservation();
            while (resultSet.next()){
                reservation.setTableId(resultSet.getInt(1));
                reservation.setCustomername(resultSet.getString(2)+" "+resultSet.getString(3));
                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Reservation> fetchallDetailsOfToday() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT FirstName, LastName, Table_ID, name, Quantity, (menuitem.price * orderitems.Quantity) AS total_price " +
                        "FROM reservations " +
                        "INNER JOIN customers ON reservations.CustomerID = customers.CustomerID " +
                        "INNER JOIN orderitems ON reservations.Reservation_ID = orderitems.Reservation_ID " +
                        "INNER JOIN menuitem ON orderitems.MenuItem_ID = menuitem.MenuItem_ID"
        )) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setCustomername(resultSet.getString(1) + " " + resultSet.getString(2));
                reservation.setTableId(resultSet.getInt(3));
                reservation.setDish(resultSet.getString(4));
                reservation.setQuantity(resultSet.getInt(5));
                reservation.setPrice(resultSet.getFloat(6));  // Fetch total price

                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addPrice(int ReservationId,float price){

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE reservations\n" +
                "SET price = ?\n" +
                "WHERE Reservation_ID=?")) {
            System.out.println("add price "+price);
            preparedStatement.setFloat(1, price);
            preparedStatement.setInt(2, ReservationId);
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
