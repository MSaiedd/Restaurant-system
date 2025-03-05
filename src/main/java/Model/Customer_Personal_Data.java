package Model;

import Database.DatabaseConnection;

public class Customer_Personal_Data {


    private static Customer_Personal_Data customerPersonalData;
    private int userid;
    private int customerid;
    private int reservationid;
    private int tableid;


    public static Customer_Personal_Data getInstance(int id){
        synchronized (DatabaseConnection.class) {
            if (customerPersonalData == null)
                customerPersonalData = new Customer_Personal_Data();
        }
        return customerPersonalData;
    }

    public static Customer_Personal_Data getInstance(){
        synchronized (DatabaseConnection.class) {
            if (customerPersonalData == null)
                customerPersonalData = new Customer_Personal_Data();
        }
        return customerPersonalData;
    }

    public static Customer_Personal_Data getCustomerPersonalData() {
        return customerPersonalData;
    }

    public static void setCustomerPersonalData(Customer_Personal_Data customerPersonalData) {
        Customer_Personal_Data.customerPersonalData = customerPersonalData;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getReservationid() {
        return reservationid;
    }

    public void setReservationid(int reservationid) {
        this.reservationid = reservationid;
    }

    public int getTableid() {
        return tableid;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
    }
}
