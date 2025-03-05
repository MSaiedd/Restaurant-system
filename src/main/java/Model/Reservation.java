package Model;

import DataAcessObjects.Reservation_DAO;
import java.util.ArrayList;

public class Reservation {
    private int reservationid;
    private int tableId;
    private int cusomerId;
    private Float price;

    private String customername;
    private int tableNumber;
    private String dish;
    private int quantity;

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getCusomerId() {
        return cusomerId;
    }

    public void setCusomerId(int cusomerId) {
        this.cusomerId = cusomerId;
    }

    public int getReservationid() {
        return reservationid;
    }

    public void setReservationid(int reservationid) {
        this.reservationid = reservationid;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    ///Float not float because it can take null value because we make reservation without price at first so we can reserve the table
    public Float getPrice() {
        if (this.price==null)
            return null;
        else
            return this.price;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Reservation(int tableId, int cusomerId, Float price) {
        this.tableId = tableId;
        this.cusomerId = cusomerId;
        if (price!=null)
            this.price = price;
    }

    public Reservation() {}

        public int makeReservation() {

        Reservation_DAO reservationDao=new Reservation_DAO();
        Reservation reservation=new Reservation(this.tableId,this.cusomerId,this.price); //add reservation to database

        if (reservationDao.addRowToReservation(reservation))
            return reservationDao.getReservationId(this.cusomerId);
        return 0;
    }

    public boolean cancelReservation(int id){
        Reservation_DAO reservationDao=new Reservation_DAO();
        System.out.println(reservationDao.removeReservation(id)); //remove reservation from database
        return true;
    }

    public ArrayList<Reservation> getReservationsOfToday(){
        Reservation_DAO reservationDao=new Reservation_DAO();
        return reservationDao.fetchReservationsOfToday();
    }

    public ArrayList<Reservation> getFullDetailsOfToday() {
        Reservation_DAO reservationDao=new Reservation_DAO();
        ArrayList<Reservation> reservations;
        reservations= reservationDao.fetchallDetailsOfToday();
        return reservations;
    }

    public boolean addPriceToReservation(int reservationid,float price){
        Reservation_DAO reservationDao=new Reservation_DAO();
        return reservationDao.addPrice(reservationid,price);
    }
}
