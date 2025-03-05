package Controllers;

import Model.OrderItem;
import com.example.remake.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Chief_Dashboard_Controller implements Initializable {

    @FXML
    private TableColumn<OrderItem, String> dishCoumn;

    @FXML
    private Label nameLabel;

    @FXML
    private TableColumn<OrderItem, Integer> quantityColumn;

    @FXML
    private TableColumn<OrderItem, Integer> tableColumn;

    @FXML
    private TableView<OrderItem> chefTableView;
    @FXML
    private Button logoutButtom;

    @FXML
    void logout() throws IOException {
        // go to login page
        Stage stage= (Stage) logoutButtom.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 400);
        stage.setTitle("Sign in");
        stage.setScene(scene);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get items included in each order to show it to the chief
        OrderItem orderItem = new OrderItem();
        ArrayList<OrderItem> orderItems = orderItem.getOrderItems();

        if (!orderItems.isEmpty()) {

            dishCoumn.setCellValueFactory(new PropertyValueFactory<>("dishName"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            tableColumn.setCellValueFactory(new PropertyValueFactory<>("tableId"));

            ObservableList<OrderItem> observableArrayList = FXCollections.observableArrayList(orderItems);
            chefTableView.setItems(observableArrayList);
           }


    }
}
