package DataAcessObjects;

import Database.DatabaseConnection;
import Model.OrderItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItem_DAO {
    public boolean addOrderItems(ArrayList<OrderItem> orderItems) throws SQLException {

        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();
        connection.setAutoCommit(false);

        //add order-items
        for (OrderItem orderItem : orderItems){
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orderitems (reservation_id, menuitem_id, quantity)\n" +
                "VALUES (?, ?, ?)")) {
            preparedStatement.setInt(1, orderItem.getReservationId());
            preparedStatement.setInt(2, orderItem.getMenuItemId());
            preparedStatement.setInt(3,orderItem.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        //commit after inserting all order items not after each one of them
        connection.commit();
        return true;
    }

    public float getPrice_x_quantity(int reservationid,int menuitemid,int quantity){

        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //get full cost of an item in order
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select Price_x_quantity from orderitems where Reservation_ID=? and MenuItem_ID=? and Quantity=?");
            preparedStatement.setInt(1, reservationid);
            preparedStatement.setInt(2, menuitemid);
            preparedStatement.setInt(3, quantity);
            ResultSet resultSet = preparedStatement.executeQuery();

            //return if output is valid
            if (!resultSet.next())
                return -1;
            else
                return resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public ArrayList<OrderItem> fetchOrderItems() {

        //get instance with sql database
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        //select order items query
        try(PreparedStatement preparedStatement = connection.prepareStatement("select Table_ID, `name`, " +
                "quantity from orderitems \n" +
                "inner join reservations on reservations.Reservation_ID=orderitems.Reservation_ID\n" +
                "inner join menuitem on orderitems.MenuItem_ID=menuitem.MenuItem_ID " +
                "WHERE DATE(reservations.date) = CURDATE()")) {

            ArrayList<OrderItem> orderItems=new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();  //excute query

            //store result in order items list them return
            while (resultSet.next()){
                OrderItem orderItem=new OrderItem();
                orderItem.setTableId(resultSet.getInt(1));
                orderItem.setDishName(resultSet.getString(2));
                orderItem.setQuantity(resultSet.getInt(3));
                orderItems.add(orderItem);
            }
            return orderItems;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}

