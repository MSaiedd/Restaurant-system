package Controllers;

import Model.User;
import com.example.remake.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class Signup_Controller {

    @FXML
    private Button back_buttom;

    @FXML
    private TextField email_textfield;

    @FXML
    private TextField first_name_textfield;

    @FXML
    private TextField last_name_textfield;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField phone_textfield;

    @FXML
    private Label error_label;

    @FXML
    void onSignUpButtonClicked() throws SQLException, IOException {
        error_label.setText("");
        Validator validator=new Validator(); //initialize validator to check inputs of user
        String first_name=first_name_textfield.getCharacters().toString().toLowerCase();
        String last_name=last_name_textfield.getCharacters().toString().toLowerCase();
        String phone=phone_textfield.getCharacters().toString();
        String email=email_textfield.getCharacters().toString();
        String password=password_field.getCharacters().toString();

        //check inputs then add user
        if (!validator.check_name(first_name)){
            error_label.setText("WRONG FIRST NAME");
        }else if (!validator.check_name(last_name)) {
            error_label.setText("WRONG LAST NAME");
        }else if (!validator.check_phone(phone)) {
            error_label.setText("WRONG PHONE");
        }else if (!validator.check_email(email)) {
            error_label.setText("WRONG EMAIL");
        }else if (!validator.check_password(password)) {
            error_label.setText("WRONG PASSWORD");
        }else{
            User user = new User(email,password);
            if (user.addUser(user,first_name,last_name,phone))
                System.out.println("user added successifully");
            back();
        }
    }
    @FXML
    void back() throws IOException {
        ///move to sign in view
        Stage stage= (Stage) back_buttom.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 400);
        stage.setTitle("Sign in");
        stage.setScene(scene);
    }

}
