package nguyenpeter.c195_pa;

import database.DBCountries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import model.Country;
import nguyenpeter.c195_pa.MainApplication;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
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
    void onLoginButton(ActionEvent event) throws SQLException, IOException {
        if (rButtonEng.isSelected()) {
            if (usernameTextField.getText().equals("") || passwordField.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please enter a username or password");
                alert.show();
            }
            if (usernameTextField.getText().equals("test") && passwordField.getText().equals("test") ||
                    usernameTextField.getText().equals("admin") && passwordField.getText().equals("admin")){
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("Main.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Incorrect username or password");
                alert.show();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rButtonEng.setSelected(true);
        timeZone.setText(String.valueOf(ZoneId.systemDefault()));
    }

}
