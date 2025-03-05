package Model;

import DataAcessObjects.OrderItem_DAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItem {
    private int reservationId;
    private int menuItemId;
    private int quantity;

    private int tableId;
    private Float price;

    private String dishName;

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Float price) {
        this.price = price;
    }


    public OrderItem(){}
    public OrderItem(int reservationid,int menuitemid,int quantity,String dishName) {
        this.dishName=dishName;
        this.quantity=quantity;
        this.reservationId=reservationid;
        this.menuItemId=menuitemid;
        OrderItem_DAO orderItemDao = new OrderItem_DAO();
        this.price=orderItemDao.getPrice_x_quantity(reservationid,menuitemid, quantity);

    }

    public boolean  addItems(ArrayList<OrderItem> orderItems) throws SQLException {
        OrderItem_DAO orderItemDao=new OrderItem_DAO();
        return orderItemDao.addOrderItems(orderItems);
    }


    public ArrayList<OrderItem> getOrderItems() {
        OrderItem_DAO orderItemDao=new OrderItem_DAO();
        return orderItemDao.fetchOrderItems();
    }

    public float getTotalPrice(ArrayList<OrderItem> orderItems){
        float temp=0;
        for (OrderItem orderItem:orderItems)
            temp=temp+(orderItem.getPrice());
        return temp;
    }
}
