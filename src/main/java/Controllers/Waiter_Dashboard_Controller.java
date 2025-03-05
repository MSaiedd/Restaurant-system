package Controllers;

import Model.OrderItem;
import Model.Reservation;
import com.example.remake.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Waiter_Dashboard_Controller implements Initializable {
    @FXML
    private TableColumn<Reservation, String> customerNameColumn;

    @FXML
    private TableColumn<Reservation, Integer> tableNumberColumn;
    @FXML
    private TableView<Reservation> waiterTableView;

    @FXML
    private Button logout_button;

    @FXML
    void logout() throws IOException {
            //go to login page
            Stage stage= (Stage) logout_button.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 550, 400);
            stage.setTitle("Sign in");
            stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //show reservations
        Reservation reservation = new Reservation();
        ArrayList<Reservation> reservationList = reservation.getReservationsOfToday();
        if (!reservationList.isEmpty()) {
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customername"));
            tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableId"));

            ObservableList<Reservation> observableArrayList =FXCollections.observableArrayList(reservationList);
            waiterTableView.setItems(observableArrayList);
            ///waiterTableView.setItems((ObservableList<Reservation>) reservationList);
        }


    }
}
