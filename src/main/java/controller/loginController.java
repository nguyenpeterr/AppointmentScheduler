package controller;

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
import java.util.ResourceBundle;

/**
 * LoginController handles the login window.
 * Requires user to enter a valid username and password in order to access the application
 * Logs all login attempts in login_activity.txt file
 */
public class loginController implements Initializable {

    /**
     * Sets the stage and scene for the Login window
     */
    Stage stage;
    Parent scene;

    /**
     * Buttons and text fields are declared here from the Login.fxml
     */

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



    /**
     * Attached to the Login button for users to log in and access the application
     * Warning will show if username/password is incorrect
     * @param event On Login button click
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onLoginButton(ActionEvent event) throws SQLException, IOException {
        if (usernameTextField.getText().equals("") || passwordField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(LanguageMain.translate("missing_username_password"));
            alert.show();
        }
        else if (DBUsers.login(usernameTextField.getText(), passwordField.getText())) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Appointment Management System");
            stage.show();
        }
    }


    /**
     * Exits the application
     * @param event On Exit button click
     */
    @FXML
    void onExitButton(ActionEvent event) {
        System.exit(0);
    }

    /**
     * If the user's language setting is set to French/France, the login screen will show a translated screen, including
     * the alert message.
     * Shows the user their Time Zone on the bottom
     * @param url
     * @param resourceBundle
     */
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
