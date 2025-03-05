package Controllers;

import Model.Customer_Personal_Data;
import Model.User;
import com.example.remake.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Login_controller {

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField username_field;

    @FXML
    private Button signup_Button1;



    @FXML
    void onSignInButtonClicked() throws IOException, SQLException {
        ///get user info
        User user = new User(username_field.getCharacters(),password_field.getCharacters());
        int user_type = user.getUserType(user);
        int id = user.getUserID(user);

        //customerpersonaldata is a static class that save the important customer data that he should keep track of
        Customer_Personal_Data customerPersonalData=Customer_Personal_Data.getInstance(id);
        customerPersonalData.setUserid(user.getUserID(user));   //add user id to personal data

        //(0,customer) (2,cooker) (3,waiter) (4,manager) (else,useless anyway could be removed as we already checked for wrong data)
        Map<Integer, String> userTypeToFxmlMap = new HashMap<>();
        userTypeToFxmlMap.put(0, "Reserve_Table.fxml");
        userTypeToFxmlMap.put(1, "Cooker_Dashboard.fxml");
        userTypeToFxmlMap.put(2, "Waiter_Dashboard.fxml");
        userTypeToFxmlMap.put(3, "Manager_Dashboard.fxml");
        String fxmlFile = userTypeToFxmlMap.get(user_type);

        if (fxmlFile != null) {
            Stage stage= (Stage) signup_Button1.getScene().getWindow();
            stage.setTitle("Sign up");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load(), 550, 400);
            stage.setScene(scene);
        } else {
            System.out.println("WRONG OUTPUT " + user_type);
        }

    }

    @FXML
    void goToSignupView() throws IOException {
        ///move to signup view
        Stage stage= (Stage) signup_Button1.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 400);
        stage.setTitle("Sign up");
        stage.setScene(scene);
    }

}
