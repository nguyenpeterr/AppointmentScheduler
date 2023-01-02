package controller;

import database.DBCountries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton rButtonEng;

    @FXML
    private RadioButton rButtonFrench;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label languageLabel;;

    @FXML
    private Label timeZoneLabel;

    @FXML
    private Label timeZone;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    void onRadioSelect(ActionEvent event) {

    }

    @FXML
    void onLoginButton(ActionEvent event) throws SQLException {
        if (usernameTextField.getText().equals("") || passwordField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter a username or password");
            alert.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rButtonEng.setSelected(true);

    }

}
