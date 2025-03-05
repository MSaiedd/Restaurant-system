package Controllers;

import Model.Reservation;
import Model.User;
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

public class Manager_Dashboard_Controller implements Initializable {

    @FXML
    private TableColumn<Reservation, Float> costColumn;   //add price of order

    @FXML
    private TableColumn<Reservation, String> customerNameColumn;

    @FXML
    private TableColumn<Reservation, Integer> quantityColumn;


    @FXML
    private TableColumn<Reservation, String> dishColumn;

    @FXML
    private Button logout_button;

    @FXML
    private TableView<Reservation> managerTableView;

    @FXML
    private Label nameLabel;   //add manager name

    @FXML
    private TableColumn<Reservation, Integer> tableNumberColumn;

    @FXML
    private TextField ageTextField;

    @FXML
    private Button back_buttom;

    @FXML
    private PasswordField emailTextField;

    @FXML
    private Label error_label;

    @FXML
    private TextField first_name_textfield;

    @FXML
    private TextField last_name_textfield;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField phone_textfield;

    @FXML
    private PasswordField salaryTextField;

    @FXML
    private ComboBox<String> roleComboBox;
    ObservableList<String> items = FXCollections.observableArrayList(
            "Chef",
            "Waiter",
            "Manager"
    );

    @FXML
    void addWorker() throws IOException {
        //go to add worker page
        Stage stage= (Stage) logout_button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Worker_Signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 400);
        stage.setTitle("Sign in");
        stage.setScene(scene);
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
    void back() throws IOException {
        //go to manager dashboard page
        Stage stage= (Stage) back_buttom.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Manager_Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 400);
        stage.setTitle("Sign in");
        stage.setScene(scene);
    }

    @FXML
    void onSignUpButtonClicked() throws SQLException, IOException {
        error_label.setText("");
        Validator validator=new Validator();
        String first_name=first_name_textfield.getCharacters().toString().toLowerCase();
        String last_name=last_name_textfield.getCharacters().toString().toLowerCase();
        String role=roleComboBox.getValue();
        String age= ageTextField.getText();
        String salary= salaryTextField.getText();
        String phone=phone_textfield.getCharacters().toString();
        String email=emailTextField.getCharacters().toString();
        String password=password_field.getCharacters().toString();

        if (!validator.check_name(first_name)){
            error_label.setText("WRONG FIRST NAME");
        }else if (!validator.check_name(last_name)) {
            error_label.setText("WRONG LAST NAME");
        }else if (!validator.check_age(age)){
            error_label.setText("WRONG AGE");
        }else if (!validator.check_salary(salary)){
            error_label.setText("WRONG SALARY");
        }else if (!validator.check_phone(phone)) {
            error_label.setText("WRONG PHONE");
        }else if (!validator.check_email(email)) {
            error_label.setText("WRONG EMAIL");
        }else if (!validator.check_password(password)) {
            error_label.setText("WRONG PASSWORD");
        }else{
            User user = new User(email,password);
            System.out.println("------------------------------------------------------------");
            if (user.addUser(user,first_name,last_name,role,age,salary,phone)) {
                System.out.println("user added successifully");
                back();
            }
            else
            {
                error_label.setText("dublicate email");
                System.out.println("Error");
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (roleComboBox != null) {
            roleComboBox.setItems(items);
            roleComboBox.setValue(items.get(1));
        } else {
            Reservation reservation = new Reservation();
            ArrayList<Reservation> reservationList = reservation.getFullDetailsOfToday();

            if (!reservationList.isEmpty()) {
                customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customername"));
                tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableId"));
                dishColumn.setCellValueFactory(new PropertyValueFactory<>("dish"));
                quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                costColumn.setCellValueFactory(new PropertyValueFactory<>("price"));  // ✅ Now setting the price column

                ObservableList<Reservation> observableArrayList = FXCollections.observableArrayList(reservationList);
                managerTableView.setItems(observableArrayList);
            }
        }
    }
}
