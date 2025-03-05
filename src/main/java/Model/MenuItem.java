package Model;

import DataAcessObjects.MenuItem_DAO;
import java.util.ArrayList;

public class MenuItem {
    private String name;
    private int price;
    private String type;

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MenuItem(){}
    public ArrayList<MenuItem> getMenuItems(String type) {

        ArrayList<MenuItem> menuItems=new ArrayList<>();  //temp menu-items list

        MenuItem_DAO menuItemDao = new MenuItem_DAO();
        ArrayList<String> arrayList = menuItemDao.getMenuItemsByType(type); //get items from database

        MenuItem menuItem;
        //save items to temp list then return
        while (!arrayList.isEmpty()) {
            menuItem=new MenuItem();
            menuItem.setName(arrayList.remove(0));
            menuItem.setPrice(Integer.parseInt(arrayList.remove(0)));
            menuItem.setType(type);
            menuItems.add(menuItem);

        }
    return menuItems;
    }


    public int getItemIdbyName(String name) {
        MenuItem_DAO menuItemDao=new MenuItem_DAO();
        return menuItemDao.getMenuIdByName(name);

    }

    public float getItemPricebyName(String name) {
        MenuItem_DAO menuItemDao=new MenuItem_DAO();
        return menuItemDao.getMenuPriceByName(name);

    }
}
