package Model;

import DataAcessObjects.Table_DAO;

public class Table {
    private String area;
    private int max_seats;
    private String status;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getMax_seats() {
        return max_seats;
    }

    public void setMax_seats(int max_seats) {
        this.max_seats = max_seats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private Table(String area , int max_seats , String status){
        this.area=area;
        this.max_seats=max_seats;
        this.status=status;
    }
    public Table(){}
    public int getEmptyTable(String area , int seats){

        Table_DAO tableDao=new Table_DAO();
        Table table=new Table(area , seats , "not_reserved"); //change status of table in database
        return tableDao.reserveTable(table);
    }
    public boolean makeItNotReserved(int id){
        Table_DAO tableDao=new Table_DAO();
        tableDao.removeReservedStatus(id);  //cancel reservation in database
        return true;


    }
}
