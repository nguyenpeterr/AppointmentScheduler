package nguyenpeter.c195_pa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private Label addCustomerLabel;
    @FXML
    private TextField addressField;
    @FXML
    private Label addressLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<?> countryComboBox;
    @FXML
    private Label countryLabel;
    @FXML
    private TextField customerIdField;
    @FXML
    private Label customerIdLabel;
    @FXML
    private ComboBox<?> divisionComboBox;
    @FXML
    private TextField nameField;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField phoneField;
    @FXML
    private Label phoneLabel;
    @FXML
    private TextField postalCodeField;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Label stateLabel;

    @FXML
    void onCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(MainController.class.getResource("Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onCountryComboBox(ActionEvent event) {

    }

    @FXML
    void onSaveButton(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
