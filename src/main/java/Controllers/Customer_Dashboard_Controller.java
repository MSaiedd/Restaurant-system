package Controllers;

import Model.MenuItem;
import Model.*;
import com.example.remake.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Customer_Dashboard_Controller implements Initializable {


    @FXML
    private Label table_status;
    @FXML
    private Spinner<Integer> seatsSpinner;

    @FXML
    private ToggleGroup Smoking_Area;
    @FXML
    private  Button reserve_button;
    @FXML
    private Button logout_button;

    //////////////////////////////////////////////////

    @FXML
    private Button back_button;

    @FXML
    private Spinner<Integer> quantity;


    @FXML
    private TableColumn<OrderItem, String> dishcolumn;

    @FXML
    private TableColumn<OrderItem, Float> pricecolumn;

    @FXML
    private TableColumn<OrderItem, Integer> quantitycolumn;
    @FXML
    private TableView<OrderItem> table_view;

    @FXML
    private ComboBox<String> dish_combobox;
    @FXML
    private ComboBox<String> dish_type_combobox;

    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,15,1);

    ObservableList<String> items = FXCollections.observableArrayList(
            "Main",
            "Appetizer",
            "Drink"
    );



    @FXML
    void goToReserveTable() throws IOException  {
        //get tableid
        Customer_Personal_Data customerPersonalData= Customer_Personal_Data.getInstance();

        //cancel reservation (make table not reserved then cancel reservation )
        Table table=new Table();
        table.makeItNotReserved(customerPersonalData.getTableid());

        Reservation reservation=new Reservation();
        reservation.cancelReservation(customerPersonalData.getReservationid());

        //go back to reserve table page
        Stage stage= (Stage) back_button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Reserve_Table.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 400);
        stage.setTitle("Sign in");
        stage.setScene(scene);
    }

    @FXML
    void goToMakeOrderPage() throws IOException, SQLException {
        String area= ((ToggleButton) Smoking_Area.getSelectedToggle()).getText();


        //check if table is available
        Table table=new Table();
        int tableid=table.getEmptyTable(area,seatsSpinner.getValue());

        if (tableid!=0){
            ///RESERVE TABLE WITH ID
            Customer_Personal_Data customerPersonalData=Customer_Personal_Data.getInstance();
            customerPersonalData.setTableid(tableid);

            ///set customer id
            Customer customer=new Customer();
            int customerId = customer.getCustomerIdByUserId(customerPersonalData.getUserid());
            customerPersonalData.setCustomerid(customerId);

            ///make reservation
            Reservation reservation=new Reservation(tableid,customerId,null);
            int id =reservation.makeReservation();

            //if reservation made successfully go to make order page
            if (id!=0)
                customerPersonalData.setReservationid(id);

            //go to order page
            Stage stage = (Stage) reserve_button.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Make_Order.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 550, 400);
            stage.setTitle("Make Order"); // Set the title for the new scene
            stage.setScene(scene);
        }else
            table_status.setVisible(true);

    }

    @FXML
    void logout() throws IOException {
        //go to login page
        Stage stage= (Stage) logout_button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 400);
        stage.setTitle("Sign in");
        stage.setScene(scene);

    }

    @FXML
    void getMenuItems() throws SQLException {

        //get menu item corresponding to type selected by user
        Model.MenuItem menuItem=new MenuItem();
        ArrayList<MenuItem> menuItems = menuItem.getMenuItems(dish_type_combobox.getValue());

        // Create an ObservableList of Strings to store the names
        ObservableList<String> data = FXCollections.observableArrayList();
        if (!menuItems.isEmpty()){

            for (MenuItem item : menuItems)
                data.add(item.getName());

            // Now, set the data to your TableView
            dish_combobox.setItems(data);
            dish_combobox.setValue(data.get(0));
        }

}

    @FXML
    void addItem() throws SQLException {

        //get dish info
        String dish = dish_combobox.getValue().toString();
        int dish_quantity= quantity.getValue();

        MenuItem menuItem=new MenuItem();
        int dish_id=menuItem.getItemIdbyName(dish);

        //create item
        Customer_Personal_Data customerPersonalData = Customer_Personal_Data.getInstance();
        OrderItem orderItem = new OrderItem(customerPersonalData.getReservationid(),dish_id,dish_quantity,dish);
        orderItem.setPrice(menuItem.getItemPricebyName(dish)*dish_quantity);

        ///add item to order items list (without confirmation it won't be added to database)
        ObservableList<OrderItem> orderItems = table_view.getItems();
        orderItems.add(orderItem);
        table_view.setItems(orderItems);
    }
    @FXML
    void removeItem() {
        //remove selected item from order items list
        int selectedId= table_view.getSelectionModel().getSelectedIndex();
        if (selectedId!=-1)
            table_view.getItems().remove(selectedId);
    }

    @FXML
    void confirmOrder() throws SQLException, IOException {

        // Convert order items to a List<OrderItem>
        ObservableList<OrderItem> orderItems = table_view.getItems();
        ArrayList<OrderItem> orderItemsList = new ArrayList<>(orderItems);

        if (!orderItemsList.isEmpty()) {

            //add orderitems to database
            orderItemsList.get(0).addItems(orderItemsList);

            //add total price to reservation (when it created it was null as the customer haven't made the order yet)
            Reservation reservation=new Reservation();
            Customer_Personal_Data customerPersonalData = Customer_Personal_Data.getInstance();
            reservation.addPriceToReservation(customerPersonalData.getReservationid(),orderItemsList.get(0).getTotalPrice(orderItemsList));
            System.out.println("test"+table_view.getItems().get(3));
            System.out.println("price of reservation"+orderItemsList.get(0).getTotalPrice(orderItemsList));
            //order made go back to login page
            Stage stage = (Stage) back_button.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("lOGIN.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 550, 400);
            stage.setTitle("Sign in");
            stage.setScene(scene);
        }else
            System.out.println("list is empty in confirm");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //as that controller have 2 pages we have to check the values of spinners to know what page we are in
        if (seatsSpinner==null) {
            quantity.setValueFactory(svf);

            dish_type_combobox.setItems(items);
            dish_type_combobox.setValue(items.get(0));

            dishcolumn.setCellValueFactory(new PropertyValueFactory<>("dishName"));
            quantitycolumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            pricecolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            try {
                getMenuItems();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            seatsSpinner.setValueFactory(svf);
    }



}

