package nguyenpeter.c195_pa;

import database.DBUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.LanguageMain;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
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
    private Button exitButton;

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
    void onLoginButton(ActionEvent event) throws SQLException, IOException {
        if (usernameTextField.getText().equals("") || passwordField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter a valid username or password");
            alert.show();
        }
        else if (DBUsers.login(usernameTextField.getText(), passwordField.getText())) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle(LanguageMain.translate("Appointment Management System"));
            stage.show();
        }
    }

    @FXML
    void onExitButton(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(LanguageMain.translate("username"));
        passwordLabel.setText(LanguageMain.translate("password"));
        loginButton.setText(LanguageMain.translate("login"));
        exitButton.setText(LanguageMain.translate("exit"));
        timeZoneLabel.setText(LanguageMain.translate("timezone"));



        timeZone.setText(String.valueOf(ZoneId.systemDefault()));
    }

}
